package com.example.demo;

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
@RequestMapping("/api/v1/inventory/products")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@PostMapping("/add")
	public ProductDao addProduct(@RequestBody ProductDao productDao) {
		return productService.addProduct(productDao);
	}
	
	@PutMapping("/update/{id}")
	public ProductDao updateProduct(@PathVariable int id, @RequestBody ProductDao productDao) {
		return productService.updateProduct(id, productDao);
	}
	
	@DeleteMapping("/{id}")
	public ProductDao deleteProduct(@PathVariable int id) {
		return productService.deleteProduct(id);
	}	
	
	@GetMapping("/{id}")
	public ProductDao getProductDetails(@PathVariable int id){
		return productService.getProductDetails(id);
	}
}
