package com.sherwin.WBMDomainService.models;

import java.time.LocalDateTime;

import com.sherwin.WBMDomainService.validatorGroupsI.Create;
import com.sherwin.WBMDomainService.validatorGroupsI.Update;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

public class WorkOrderDTO {
    public String workOrderId;
    public String workOrderNumber;

    @NotBlank(groups = Create.class, message = "itemNumber is required")
    @Pattern(
            groups = {Create.class, Update.class},
            regexp = "^it-\\d+$",
            message = "format should be it-<number>"
    )
    public String itemNumber;

    @NotBlank(groups = Create.class, message = "organizationCode is required")
    @Pattern(
            groups = {Create.class, Update.class},
            regexp = "^[a-zA-Z]+$",
            message = "organization code should be chars only"
    )
    public String organizationCode;

    @NotNull(groups = Create.class, message = "batchQuantity is required")
    @Min(groups = {Create.class, Update.class},value = 1, message = "minimum batchquantity must be 1")
    public Integer batchQuantity;

    @NotNull(groups = Create.class, message = "plannedStartDate is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime  plannedStartDate;

    @NotNull(groups = Create.class, message = "plannedEndDate is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime plannedEndDate;

    public String getWorkOrderId() {
        return workOrderId;
    }

    public String getWorkOrderNumber() {
        return workOrderNumber;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public Integer getBatchQuantity() {
        return batchQuantity;
    }

    public void setBatchQuantity(Integer batchQuantity) {
        this.batchQuantity = batchQuantity;
    }

    public LocalDateTime getPlannedStartDate() {
        return plannedStartDate;
    }

    public void setPlannedStartDate(LocalDateTime plannedStartDate) {
        this.plannedStartDate = plannedStartDate;
    }

    public LocalDateTime getPlannedEndDate() {
        return plannedEndDate;
    }

    public void setPlannedEndDate(LocalDateTime plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }
}
