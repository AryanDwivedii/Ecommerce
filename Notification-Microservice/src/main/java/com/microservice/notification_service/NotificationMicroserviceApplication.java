package com.microservice.notification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class NotificationMicroserviceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(NotificationMicroserviceApplication.class, args);}
	
	@KafkaListener(topics = "notificationTopic")
	public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
		log.info("Notification received for order - {}", orderPlacedEvent.getOrderNumber());
	
	
	
	
	}
}
