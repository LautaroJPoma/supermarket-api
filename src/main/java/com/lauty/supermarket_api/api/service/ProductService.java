package com.lauty.supermarket_api.api.service;

import java.util.List;

import com.lauty.supermarket_api.api.dto.ProductDTO;

public interface ProductService {
    ProductDTO getProductById(Long id);

    List<ProductDTO> getAllProducts();

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    void deleteProduct(Long id);
}
