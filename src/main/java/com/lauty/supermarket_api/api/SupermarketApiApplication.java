package com.lauty.supermarket_api.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class SupermarketApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupermarketApiApplication.class, args);
	}

}
