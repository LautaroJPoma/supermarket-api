package com.lauty.supermarket_api.api.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class CategoryDTO {

    @Schema(hidden = true)
    private Long id;

    private String name;
    private String description;

    @Schema(hidden = true)
    private List<Long> productIds;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

}
