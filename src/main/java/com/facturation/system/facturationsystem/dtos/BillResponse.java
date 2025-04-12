package com.facturation.system.facturationsystem.dtos;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

public class BillResponse {
    private Long id;
    private String billNumber;
    private LocalDateTime date;
    private Long costumerId;
    private String costumerName;
    private BigDecimal total;
    private List<BillDetailResponse> details;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getCostumerId() {
        return costumerId;
    }

    public void setCostumerId(Long costumerId) {
        this.costumerId = costumerId;
    }

    public String getCostumerName() {
        return costumerName;
    }

    public void setCostumerName(String costumerName) {
        this.costumerName = costumerName;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<BillDetailResponse> getDetails() {
        return details;
    }

    public void setDetails(List<BillDetailResponse> details) {
        this.details = details;
    }
}
