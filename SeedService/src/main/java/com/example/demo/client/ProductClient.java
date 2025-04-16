package com.example.demo.client;

import com.example.demo.data.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "productmanagement", url = "http://localhost:8081/api/v1/inventory")
public interface ProductClient {

     @PostMapping("/products/add")
     @ResponseBody ProductDTO addProduct(@RequestBody ProductDTO product);

    // @PutMapping("/products/{id}")
    // void updateProduct(@PathVariable("id") Long id, @RequestBody ProductDTO product);

    // @DeleteMapping("/products/{id}")
    // void deleteProduct(@PathVariable("id") Long id);
}
