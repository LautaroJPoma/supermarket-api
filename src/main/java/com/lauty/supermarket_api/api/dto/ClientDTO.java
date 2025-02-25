package com.lauty.supermarket_api.api.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class ClientDTO {

    @Schema(hidden = true)
    private Long id;

    private String name;
    private String email;

    @Schema(hidden = true)
    private List<Long> purchaseOrderIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Long> getPurchaseOrderIds() {
        return purchaseOrderIds;
    }

    public void setPurchaseOrderIds(List<Long> purcharseOrderIds) {
        this.purchaseOrderIds = purcharseOrderIds;
    }

}
