package com.facturation.system.facturationsystem.dtos;

public class BillDetailRequest {
    private Long productId;
    private Integer quantity;

    // Getters y Setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
