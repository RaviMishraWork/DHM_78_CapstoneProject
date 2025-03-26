package com.example.demo.controller;

import org.apache.hc.core5.http.io.entity.HttpEntities;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @PostMapping("/add")
    public String addProduct() {
        // Todo
        return "success";
    }

    @PutMapping("/update/{id}")
    public String updateProduct(@RequestBody Product product, @PathVariable String id) {
        // Todo
        return "success";
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable String id) {
        // Todo
        return "success";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable String id) {
        // Todo
        return "success";
    }
}
