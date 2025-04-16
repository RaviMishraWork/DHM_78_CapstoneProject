package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class ProductManagementApplication {

	public static void main(String[] args) {
		System.out.println(System.getenv());
		SpringApplication.run(ProductManagementApplication.class, args);
	}

}
