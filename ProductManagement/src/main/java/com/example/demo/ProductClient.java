package com.example.demo;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

//Annotation for interfaces declaring that a REST client with that interface should be created (e.g. for autowiring into another component).
@FeignClient(name="inventorystock",url="localhost:8082/stockapi")
public interface ProductClient {
	//getting the inventory alerts
	@GetMapping("/alerts")
	public List<InventoryStockDao> getAlerts();
	
	//adding product to the inventory
	@PostMapping("/add")
	public InventoryStockDao addToInventory(InventoryStockDao  inventoryStockDao);
		
	//deleting product from the inventory
	@DeleteMapping("/sku/{sku}")
	public InventoryStockDao deleteFromInventory(@PathVariable ("sku") Integer sku);
	
}
