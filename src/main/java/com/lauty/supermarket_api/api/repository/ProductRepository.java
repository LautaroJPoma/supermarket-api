package com.lauty.supermarket_api.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lauty.supermarket_api.api.model.Category;

import com.lauty.supermarket_api.api.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}
