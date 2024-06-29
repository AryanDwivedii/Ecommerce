package com.microservice.order_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.order_service.model.Order;

public interface OrderRepo extends JpaRepository<Order, Long>{

}
