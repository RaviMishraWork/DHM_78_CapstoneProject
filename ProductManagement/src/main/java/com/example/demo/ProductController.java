package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventory")
public class ProductController {//localhost:8081/api/v1/inventory
	@Autowired
	private ProductService productService;
	
	@PostMapping("/products/add")
	public ProductDao addProduct(@RequestBody ProductDao productDao) {
		return productService.addProduct(productDao);
	}
	
	@PutMapping("/products/update/{id}")
	public ProductDao updateProduct(@PathVariable int id, @RequestBody ProductDao productDao) {
		return productService.updateProduct(id, productDao);
	}
	
	@DeleteMapping("/products/{id}")
	public ProductDao deleteProduct(@PathVariable int id) {
		return productService.deleteProduct(id);
	}	
	
	@GetMapping("/products/{id}")
	public ProductDao getProductDetails(@PathVariable int id){
		return productService.getProductDetails(id);
	}
	@PutMapping("/products/sku/{sku}")
	public ProductDao updateProductBySku(@PathVariable int sku, @RequestBody ProductDao productDao) {
		return productService.updateProductBySku(sku, productDao);
	}
	
	@DeleteMapping("/products/sku/{sku}")
	public ProductDao deleteProductBySku(@PathVariable int sku) {
		return productService.deleteProductBySku(sku);
	}	
	
	@GetMapping("/products/sku/{sku}")
	public ProductDao getProductDetailsBySku(@PathVariable int sku){
		return productService.getProductDetailsBySku(sku);
	}
	@GetMapping("/products")
	public List<ProductDao> getAllProductDetails() {
		return productService.getAllProductDetails();
	}
	
	@GetMapping("/alerts")
	public List<InventoryStockDao> getAlerts() {	
		return productService.getAlerts() ;
	}
	
	@PostMapping("/orders/place")
	public OrderDTO sendOrder(@RequestBody OrderDTO orderRequestDTO) {
		return productService.sendOrder(orderRequestDTO);
	}
}
