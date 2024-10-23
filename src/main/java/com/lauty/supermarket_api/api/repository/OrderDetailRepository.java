package com.lauty.supermarket_api.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.lauty.supermarket_api.api.model.OrderDetail;


public interface OrderDetailRepository extends CrudRepository<OrderDetail, Long> {

}
