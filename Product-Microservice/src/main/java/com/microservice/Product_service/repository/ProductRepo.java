package com.microservice.Product_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.microservice.Product_service.model.Product;

public interface ProductRepo extends MongoRepository<Product, String>{

}
