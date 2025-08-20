package com.lauty.supermarket_api.api.service;

import java.util.List;

import com.lauty.supermarket_api.api.dto.BrandDTO;


public interface BrandService {

    BrandDTO getBrandById(Long id);

    BrandDTO createBrand(BrandDTO brandDTO);

    List<BrandDTO> getAllBrands();

    BrandDTO updateBrand(Long id, BrandDTO brandDTO);

    void deleteBrand(Long id);

    boolean existsById(Long id);

}
