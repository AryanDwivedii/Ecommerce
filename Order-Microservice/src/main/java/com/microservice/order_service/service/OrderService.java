package com.microservice.order_service.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.microservice.order_service.dto.InventoryDto;
import com.microservice.order_service.dto.OrderItemsDto;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


import com.microservice.order_service.dto.OrderRequestDTO;
import com.microservice.order_service.dto.OrderResponseDTO;
import com.microservice.order_service.event.OrderPlacedEvent;
import com.microservice.order_service.model.Order;
import com.microservice.order_service.model.OrderItems;
import com.microservice.order_service.repo.OrderRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
	private final OrderRepo orderRepo;
	
	
	private final WebClient webClient;
	
	private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate; 
		
	public String placeOrder(OrderRequestDTO orderRequestDTO) throws IllegalArgumentException {
		Order order= new Order();	
		order.setOrderNumber(UUID.randomUUID().toString());
		List<OrderItems> orderItems=orderRequestDTO.getOrderItemsDto()
						.stream().map(orderItemsDto -> mapToDto(orderItemsDto)).toList();
		order.setOrderItemsList(orderItems);
		List<String> skuCodes=order.getOrderItemsList().stream().map(orderItem -> orderItem.getSkuCode()).toList();
		
		InventoryDto[] inventoryDtos=webClient.get()
						.uri("http://localhost:8082/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
						.retrieve()
							.bodyToMono(InventoryDto[].class)
								.block();
			boolean AllInStock=Arrays.stream(inventoryDtos).allMatch(inventoryResponse -> inventoryResponse.isInStock());
		
		if(AllInStock) {
			orderRepo.save(order);
			kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
			return "Order Placed Successfully!";
		}else {throw new IllegalArgumentException("Product is not in stock");
			}
		
	}
	
	private OrderItems mapToDto(OrderItemsDto orderItemsDto) {
		OrderItems orderItems=new OrderItems();
		orderItems.setPrice(orderItemsDto.getPrice());
		orderItems.setQuantity(orderItemsDto.getQuantity());
		orderItems.setSkuCode(orderItemsDto.getSkuCode());
		
		return orderItems;
	}
	
	public List<OrderResponseDTO> getAllOrders() {
	    List<Order> orders = orderRepo.findAll();
	    return orders.stream()
	            .map(order -> mapToDto(order))
	            .collect(Collectors.toList());
	}
	
	private OrderResponseDTO mapToDto(Order order) {
	    OrderResponseDTO orderDto = new OrderResponseDTO();
	    orderDto.setId(order.getId());
	    orderDto.setOrderNumber(order.getOrderNumber());
	    // Convert List<OrderItems> to List<OrderItemsDto>
	    List<OrderItemsDto> orderItemsDtoList = order.getOrderItemsList().stream()
	            .map(orderItems -> {
	                OrderItemsDto orderItemsDto = new OrderItemsDto();
	                
	                orderItemsDto.setSkuCode(orderItems.getSkuCode());
	                orderItemsDto.setPrice(orderItems.getPrice());
	                orderItemsDto.setQuantity(orderItems.getQuantity());
	               
	                
	                return orderItemsDto;
	            })
	            .collect(Collectors.toList());
	    orderDto.setOrderItemsList(orderItemsDtoList);
	    return orderDto;
	}

	
}
