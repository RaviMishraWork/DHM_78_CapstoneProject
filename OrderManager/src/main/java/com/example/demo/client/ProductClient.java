package com.example.demo.client;

import com.example.demo.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "", url = "/api/v1/inventory")
public interface ProductClient {

    @PostMapping("/products/add")
    void addProduct(@RequestBody ProductDTO product);

    @PutMapping("products/update/{id}")
    void updateProduct(@RequestBody ProductDTO product, @PathVariable int id);

    @GetMapping("/products/{id}")
    ProductDTO getProduct(@PathVariable int id);
}
