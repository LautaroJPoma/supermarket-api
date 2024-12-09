package com.lauty.supermarket_api.api.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.lauty.supermarket_api.api.dto.CategoryDTO;
import com.lauty.supermarket_api.api.model.Category;
import com.lauty.supermarket_api.api.model.Product;

@Component
public class CategoryMapper {

    public CategoryDTO toDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());

        if (category.getProducts() != null) {
            List<Long> productIds = category.getProducts()
                    .stream()
                    .map(Product::getId)
                    .collect(Collectors.toList());
            categoryDTO.setProductIds(productIds);
        } else {
            categoryDTO.setProductIds(new ArrayList<>());
        }
        return categoryDTO;
    }

    public Category toEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return category;
    }
}
