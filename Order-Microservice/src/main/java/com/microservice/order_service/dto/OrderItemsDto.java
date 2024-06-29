package com.microservice.order_service.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDto {
	@JsonIgnore
	private Long id;
	private String skuCode;
	private BigDecimal price;
	private Integer quantity;
}
