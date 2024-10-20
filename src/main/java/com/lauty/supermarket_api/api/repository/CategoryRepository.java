package com.lauty.supermarket_api.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.lauty.supermarket_api.api.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
