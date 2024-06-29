package com.microservice.order_service.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.order_service.dto.OrderRequestDTO;
import com.microservice.order_service.dto.OrderResponseDTO;
import com.microservice.order_service.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
		private final OrderService orderService;
		
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@CircuitBreaker(name= "inventory", fallbackMethod = "fallbackMethod")
	@TimeLimiter(name= "inventory")
	@Retry(name="inventory")
	public CompletableFuture<String> placeOrder(@RequestBody OrderRequestDTO orderRequestDto) throws IllegalArgumentException {
		return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequestDto));
		
	}
	
	public CompletableFuture<String> fallbackMethod(OrderRequestDTO orderRequestDTO, RuntimeException runtimeException) {
		return CompletableFuture.supplyAsync(() -> "Oops! We are having lunch. Please try again in sometime");
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<OrderResponseDTO> getAllOrders(){
		return orderService.getAllOrders();
	}
	
}
