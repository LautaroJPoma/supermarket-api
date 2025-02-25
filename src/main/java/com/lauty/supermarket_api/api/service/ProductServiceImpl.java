package com.lauty.supermarket_api.api.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.lauty.supermarket_api.api.dto.ProductDTO;
import com.lauty.supermarket_api.api.mapper.ProductMapper;
import com.lauty.supermarket_api.api.model.Product;
import com.lauty.supermarket_api.api.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO getProductById(Long id) {

        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {

            return null;
        }
        return productMapper.toDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        Iterable<Product> productsIterable = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();

        for (Product product : productsIterable) {
            ProductDTO productDTO = productMapper.toDTO(product);
            productDTOs.add(productDTO);
        }

        return productDTOs;
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            return null;
        }

        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());

        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            productRepository.delete(product);
        }
    }

    @Override
    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }
}
