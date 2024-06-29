package com.microservice.inventory_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

import com.microservice.inventory_service.model.Inventory;
import com.microservice.inventory_service.repo.InventoryRepo;

@SpringBootApplication

public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);}
		
		@Bean
		public CommandLineRunner loadData(InventoryRepo inventoryRepo) {
			return args -> {
				Inventory inventory=new Inventory();
				inventory.setSkuCode("Iphone_16");
				inventory.setQuantity(100);
				
				Inventory inventory1=new Inventory();
				inventory1.setSkuCode("Iphone-5s");
				inventory1.setQuantity(0);
				
				inventoryRepo.save(inventory);
				inventoryRepo.save(inventory1);
			};
		}
	}


