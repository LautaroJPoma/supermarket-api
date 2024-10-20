package com.lauty.supermarket_api.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.lauty.supermarket_api.api.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
