package com.lauty.supermarket_api.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.lauty.supermarket_api.api.model.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {

}
