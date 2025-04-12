package com.facturation.system.facturationsystem.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "bill_detail")
public class BillDetailModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private BillModel bill;
    @ManyToOne
    private ProductModel product;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BillModel getBill() {
        return bill;
    }

    public void setBill(BillModel bill) {
        this.bill = bill;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
