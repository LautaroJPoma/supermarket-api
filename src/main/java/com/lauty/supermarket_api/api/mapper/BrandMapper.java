package com.lauty.supermarket_api.api.mapper;

import org.springframework.stereotype.Component;

import com.lauty.supermarket_api.api.dto.BrandDTO;
import com.lauty.supermarket_api.api.model.Brand;

@Component
public class BrandMapper {

    public Brand toEntity(BrandDTO brandDTO) {
        Brand brand = new Brand();
        brand.setId(brandDTO.getId());
        brand.setName(brandDTO.getName());
        return brand;
    }

    public BrandDTO toDTO(Brand brand) {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(brand.getId());
        brandDTO.setName(brand.getName());
        return brandDTO;
    }

}
