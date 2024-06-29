package com.microservice.inventory_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservice.inventory_service.dto.InventoryDto;
import com.microservice.inventory_service.repo.InventoryRepo;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
	
	private final InventoryRepo inventoryRepo;
	
	@Transactional(readOnly = true)
	public List<InventoryDto> isInStock(List<String> skuCode) throws InterruptedException {
		
//		 log.info("Wait Started");
//		 Thread.sleep(10000);
//		 log.info("Wait ended");
		return inventoryRepo.findBySkuCodeIn(skuCode).stream()
				.map(inventory ->
					InventoryDto.builder()
					.skuCode(inventory.getSkuCode())
					.isInStock(inventory.getQuantity() > 0)
					.build()
					).toList();
		
	}
}
