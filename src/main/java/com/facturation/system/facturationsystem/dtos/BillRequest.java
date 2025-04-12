package com.facturation.system.facturationsystem.dtos;

import java.util.List;

public class BillRequest {
    private Long costumerId;
    private List<BillDetailRequest> details;

    // Getters y Setters
    public Long getCostumerId() { return costumerId; }
    public void setCostumerId(Long costumerId) { this.costumerId = costumerId; }
    public List<BillDetailRequest> getDetails() { return details; }
    public void setDetails(List<BillDetailRequest> details) { this.details = details; }
}
