package com.microservice.order_service.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
	
	private Long id;
	private String orderNumber;
	private List<OrderItemsDto> orderItemsList;
}
