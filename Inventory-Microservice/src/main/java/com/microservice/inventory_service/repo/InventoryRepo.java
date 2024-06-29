package com.microservice.inventory_service.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.inventory_service.model.Inventory;

public interface InventoryRepo extends JpaRepository<Inventory, Long>{
	

	List<Inventory> findBySkuCodeIn(List<String> skuCode);
	
}
