package com.lauty.supermarket_api.api.dto;

import java.util.List;

public class PurchaseOrderDTO {

    private Long id;
    private Double total;
    private Long clientId;
    private List<Long> orderDetailIds;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Double getTotal() {
        return total;
    }
    public void setTotal(Double total) {
        this.total = total;
    }
    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    public List<Long> getOrderDetailIds() {
        return orderDetailIds;
    }
    public void setOrderDetailIds(List<Long> orderDetailIds) {
        this.orderDetailIds = orderDetailIds;
    }

    
}
