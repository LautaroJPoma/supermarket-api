package com.lauty.supermarket_api.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class BrandDTO {

    @Schema(hidden = true)
    private Long id;
    private String name;

    public BrandDTO() {
    }

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

}
