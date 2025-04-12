package com.example.demo.client;

import com.example.demo.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "InventoryTracking", url = "http://localhost:8082/stockapi")
public interface ProductClient {

    @PostMapping("/add")
    void addProduct(@RequestBody ProductDTO product);

    @PutMapping("/update/{id}")
    void updateProduct(@RequestBody ProductDTO product, @PathVariable int id);

    @GetMapping("/{id}")
    ProductDTO getProduct(@PathVariable int id);

    @PostMapping("/increase/sku/{sku}")
    void increaseProduct(@PathVariable int sku, @RequestBody int quantity);
}
