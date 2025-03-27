package com.example.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

//Annotation for interfaces declaring that a REST client with that interface should be created (e.g. for autowiring into another component).
@FeignClient(name="orders",url="localhost:9000/orders")
public interface OrderClient {
	@PostMapping("/place")
	public OrderRequestDTO sendOrder(OrderRequestDTO orderRequestDTO);
}
