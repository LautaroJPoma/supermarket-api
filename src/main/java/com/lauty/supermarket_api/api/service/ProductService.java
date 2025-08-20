package com.lauty.supermarket_api.api.service;

import java.util.List;

import com.lauty.supermarket_api.api.dto.ProductDTO;

public interface ProductService {
    ProductDTO getProductById(Long id);

    List<ProductDTO> getAllProducts();

    List<ProductDTO> getProductsByBrand(Long brandId);

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    void deleteProduct(Long id);

    boolean existsById(Long id);
}
