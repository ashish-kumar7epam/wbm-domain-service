package com.sherwin.WBMDomainService.controllers;

import com.sherwin.WBMDomainService.validatorGroupsI.Create;
import com.sherwin.WBMDomainService.validatorGroupsI.Update;
import com.sherwin.WBMDomainService.models.WorkOrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/DTO/workorders")
public class WorkOrderController {

    @Value("${entity.service.url}")
    private String entityServiceUrl;

    @Autowired
    private Environment env;

    private static final Logger logger =
            LoggerFactory.getLogger(WorkOrderController.class);

    private final RestTemplate restTemplate = new RestTemplate();

    // ✅ GET ALL
    @GetMapping("/")
    public List<WorkOrderDTO> getWorkOrders() {

        ResponseEntity<List<WorkOrderDTO>> response =
                restTemplate.exchange(
                        entityServiceUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<WorkOrderDTO>>() {}
                );

        return response.getBody();
    }

    // ✅ CREATE (POST → strict validation)
    @PostMapping("/new")
    public WorkOrderDTO newWorkOrder(
            @RequestBody @Validated(Create.class) WorkOrderDTO workOrderDTO
    ) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<WorkOrderDTO> request =
                new HttpEntity<>(workOrderDTO, headers);

        ResponseEntity<WorkOrderDTO> response =
                restTemplate.exchange(
                        entityServiceUrl+"newWorkOrder",
                        HttpMethod.POST,
                        request,
                        WorkOrderDTO.class
                );

        return response.getBody();
    }

    // ✅ UPDATE (PUT → partial validation like PATCH)
    @PutMapping("/update/{workOrderId}")
    public WorkOrderDTO updateWorkOrder(
            @PathVariable String workOrderId,
            @RequestBody @Validated(Update.class) WorkOrderDTO workOrderDTO
    ) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<WorkOrderDTO> request =
                new HttpEntity<>(workOrderDTO, headers);

        String url = entityServiceUrl + workOrderId;

        ResponseEntity<WorkOrderDTO> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.PUT,
                        request,
                        WorkOrderDTO.class
                );

        return response.getBody();
    }

    // ✅ DELETE
    @DeleteMapping("/delete/{workOrderId}")
    public String deleteWorkOrder(@PathVariable String workOrderId) {

        ResponseEntity<String> response =
                restTemplate.exchange(
                        entityServiceUrl + workOrderId,
                        HttpMethod.DELETE,
                        null,
                        String.class
                );

        return response.getBody();
    }
}