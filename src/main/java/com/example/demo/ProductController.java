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
@RequestMapping("/product")
public class ProductController {//localhost:8081/product
	@Autowired
	private ProductService productService;
	
	@PostMapping("/insert")
	public ProductDao addProduct(@RequestBody ProductDao productDao) {
		return productService.addProduct(productDao);
	}
	
	@PutMapping("/{sku}")
	public ProductDao updateProduct(@PathVariable int sku, @RequestBody ProductDao productDao) {
		return productService.updateProduct(sku, productDao);
	}
	
	@DeleteMapping("/{sku}")
	public ProductDao deleteProduct(@PathVariable int sku) {
		return productService.deleteProduct(sku);
	}	
	
	@GetMapping
	public List<ProductDao> getAllProducts(){
		return productService.getAllProducts();
	}
}
