package com.facturation.system.facturationsystem.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bill")
public class BillModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String billNumber;
    private LocalDateTime date;
    @ManyToOne
    private CostumerModel costumer;
    private BigDecimal total;
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<BillDetailModel> detail;
    public List<BillDetailModel> getDetail() {
        return detail;
    }

    public void setDetail(List<BillDetailModel> detail) {
        this.detail = detail;
    }

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

    public CostumerModel getCostumer() {
        return costumer;
    }

    public void setCostumer(CostumerModel costumer) {
        this.costumer = costumer;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }


}
