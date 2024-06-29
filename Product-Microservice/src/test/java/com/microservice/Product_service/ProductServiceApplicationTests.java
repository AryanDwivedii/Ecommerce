package com.microservice.Product_service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.Product_service.dto.ProductDetail;
import com.microservice.Product_service.repository.ProductRepo;
import com.mongodb.assertions.Assertions;

@SpringBootTest
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Test
	void createProducts() throws Exception {
		ProductDetail productDetail = productDetail();
		String productRequestString = objectMapper.writeValueAsString(productDetail);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
			   .contentType(MediaType.APPLICATION_JSON)
			   .content(productRequestString))
			   .andExpect(status().isCreated());
	}

	private ProductDetail productDetail() {
		return ProductDetail.builder()
					   .name("Iphone 13")
					   .description("Apple")
					   .price(BigDecimal.valueOf(1200))
					   .build();
	}
	
	@Test
	void getProducts() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
				   .contentType(MediaType.APPLICATION_JSON)
						)
				   .andExpect(status().isOk());
		Assertions.assertTrue(productRepo.findAll().size() > 0);
	}
}

//package com.microservice.Product_service;
//
//import java.math.BigDecimal;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import org.testcontainers.containers.MongoDBContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.microservice.Product_service.dto.ProductDetail;
//
//
//@SpringBootTest
//@Testcontainers
//@AutoConfigureMockMvc
//class ProductServiceApplicationTests {
//	
//	@Autowired
//	private MockMvc mockMvc;
//	
//	@Autowired
//	private ObjectMapper objectMapper;
//	
//	@Container
//	static MongoDBContainer mongoDBContainer=new MongoDBContainer("mongo:4.4.3").withExposedPorts(27017);
//	
//	@DynamicPropertySource
//	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
//        String mongoUri = String.format("mongodb://%s:%d/product-service",
//            mongoDBContainer.getHost(),
//            mongoDBContainer.getMappedPort(27017));
//        dynamicPropertyRegistry.add("spring.data.mongodb.uri", () -> mongoUri);
//    }
//	
//	@Test
//	void createProducts() throws Exception{
//		ProductDetail productDetail= productDetail();
//		String productrequestString=objectMapper.writeValueAsString(productDetail);
//		mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
//			   .contentType(MediaType.APPLICATION_JSON)
//			   .content(productrequestString))
//			   .andExpect(status().isCreated());
//	}
//	
//	
//
//
//	private ProductDetail productDetail() {
//		return productDetail().builder()
//					   .name("Iphone 13")
//					   .description("Apple")
//					   .price(BigDecimal.valueOf(1200))
//					   .build();
//	}
//	
//}
//
