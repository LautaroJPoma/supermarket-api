package com.lauty.supermarket_api.api.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lauty.supermarket_api.api.dto.ProductDTO;
import com.lauty.supermarket_api.api.model.Category;
import com.lauty.supermarket_api.api.model.Product;
import com.lauty.supermarket_api.api.repository.CategoryRepository;

@Component
public class ProductMapper {

    @Autowired
    private CategoryRepository categoryRepository; // Inyecta el repositorio de categorías

    public Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());

        // Aquí buscamos la categoría por su ID y la asignamos al producto
        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
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

        return productDTO;
    }
}
