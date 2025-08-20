package com.lauty.supermarket_api.api.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lauty.supermarket_api.api.dto.ProductDTO;
import com.lauty.supermarket_api.api.model.Brand;
import com.lauty.supermarket_api.api.model.Category;
import com.lauty.supermarket_api.api.model.Product;
import com.lauty.supermarket_api.api.repository.BrandRepository;
import com.lauty.supermarket_api.api.repository.CategoryRepository;

@Component
public class ProductMapper {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;
    

    public Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());

        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }

        if (productDTO.getBrandId() != null) {
           Brand brand = brandRepository.findById(productDTO.getBrandId())
                    .orElseThrow(() -> new RuntimeException("Brand not found"));
            product.setBrand(brand);
        }

        return product;
    }

    public ProductDTO toDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setQuantity(product.getQuantity());

        if (product.getCategory() != null) {
            productDTO.setCategoryId(product.getCategory().getId());
        }

        if (product.getBrand() != null) {
            productDTO.setBrandId(product.getBrand().getId());
        }

        return productDTO;
    }
}
