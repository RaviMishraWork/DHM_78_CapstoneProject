package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
//@EnableDiscoveryClient
@SpringBootApplication
public class InventoryTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryTrackingApplication.class, args);
	}

}
