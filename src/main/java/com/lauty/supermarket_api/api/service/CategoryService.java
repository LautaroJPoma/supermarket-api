package com.lauty.supermarket_api.api.service;

import java.util.List;

import com.lauty.supermarket_api.api.dto.CategoryDTO;

public interface CategoryService {
    CategoryDTO getCategoryById(Long id);

    List<CategoryDTO> getAllCategories();

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategory(Long id);
}
