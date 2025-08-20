package com.lauty.supermarket_api.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lauty.supermarket_api.api.dto.BrandDTO;
import com.lauty.supermarket_api.api.exception.ResourceNotFoundException;
import com.lauty.supermarket_api.api.mapper.BrandMapper;
import com.lauty.supermarket_api.api.model.Brand;
import com.lauty.supermarket_api.api.repository.BrandRepository;

@Service
public class BrandServiceImpl implements BrandService {

    public final BrandRepository brandRepository;
    public final BrandMapper brandMapper;

    public BrandServiceImpl(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public BrandDTO getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        return brandMapper.toDTO(brand);
    }

    @Override
    public List<BrandDTO> getAllBrands() {
        Iterable<Brand> brandsIterable = brandRepository.findAll();
        List<BrandDTO> brandDTOs = new ArrayList<>();
        for (Brand brand : brandsIterable) {
            BrandDTO brandDTO = brandMapper.toDTO(brand);
            brandDTOs.add(brandDTO);
        }
        return brandDTOs;
    }

    

    @Override
    public BrandDTO createBrand(BrandDTO brandDTO) {
        Brand brand = brandMapper.toEntity(brandDTO);
        Brand savedBrand = brandRepository.save(brand);
        return brandMapper.toDTO(savedBrand);
    }

    @Override
    public BrandDTO updateBrand(Long id, BrandDTO brandDTO) {
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca con ID " + id + " no encontrada"));
        existingBrand.setName(brandDTO.getName());
        Brand updatedBrand = brandRepository.save(existingBrand);
        return brandMapper.toDTO(updatedBrand);
    }

    @Override
    public void deleteBrand(Long id) {
        Brand existingBrand = brandRepository.findById(id).orElse(null);
        if (existingBrand != null) {
            brandRepository.delete(existingBrand);
        } else {
            throw new RuntimeException("Brand not found");
        }

    }

    @Override
    public boolean existsById(Long id) {
        return brandRepository.existsById(id);
    }

}
