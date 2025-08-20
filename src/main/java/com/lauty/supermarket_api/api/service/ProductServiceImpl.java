package com.lauty.supermarket_api.api.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.lauty.supermarket_api.api.dto.ProductDTO;
import com.lauty.supermarket_api.api.mapper.ProductMapper;
import com.lauty.supermarket_api.api.model.Brand;
import com.lauty.supermarket_api.api.model.Product;
import com.lauty.supermarket_api.api.repository.BrandRepository;
import com.lauty.supermarket_api.api.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final BrandRepository brandRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper,
            BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.brandRepository = brandRepository;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);

        if (productDTO.getBrandId() != null) {
            Brand brand = brandRepository.findById(productDTO.getBrandId())
                    .orElseThrow(() -> new RuntimeException("Brand not found"));
            product.setBrand(brand);
        }

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
    public List<ProductDTO> getProductsByBrand(Long brandId) {
        List<Product> products = productRepository.findByBrandId(brandId);
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            productDTOs.add(productMapper.toDTO(product));
        }
        return productDTOs;
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setQuantity(productDTO.getQuantity());

        if (productDTO.getBrandId() != null) {
            Brand brand = brandRepository.findById(productDTO.getBrandId())
                    .orElseThrow(() -> new RuntimeException("Brand not found"));
            existingProduct.setBrand(brand);
        }

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
