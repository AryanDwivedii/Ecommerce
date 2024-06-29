package com.microservice.Product_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.microservice.Product_service.dto.ProductDetail;
import com.microservice.Product_service.dto.ProductResponse;
import com.microservice.Product_service.model.Product;
import com.microservice.Product_service.repository.ProductRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {
private final ProductRepo productRepo;
	
	public ProductService(ProductRepo productRepo) {
		this.productRepo=productRepo;
	}
	
	public void createProduct(ProductDetail productDetail) {
		Product product = Product.builder()
						         .name(productDetail.getName())
						         .description(productDetail.getDescription())
						         .price(productDetail.getPrice())
						         .build();
		productRepo.save(product);
		log.info("Product {} is saved ", product.getId());
	}
	
	public List<ProductResponse> getAllProducts(){
		List<Product> products=productRepo.findAll();	
		return products.stream().map(product -> mapToProductResponse(product)).toList();
		}
	private ProductResponse mapToProductResponse(Product product) 
	{
		return ProductResponse.builder()
							  .id(product.getId())
							  .name(product.getName())
							  .description(product.getDescription())
							  .price(product.getPrice())
							  .build();
		
	}
	
}
