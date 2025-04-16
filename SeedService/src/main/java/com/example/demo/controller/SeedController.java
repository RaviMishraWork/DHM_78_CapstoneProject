package com.example.demo.controller;

import com.example.demo.service.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/seed")
public class SeedController {

    @Autowired
    private SeedService seedService;

    @GetMapping("/all")
    public Map<String, String> seed() {
        seedService.seedAll();
        HashMap<String, String> response = new HashMap<String,String>();
        response.put("message", "Seeding completed");
        response.put("data", "Seeding completed");
        response.put("successful", "true");
        return response;
    }
    @GetMapping("/suppliers")
    public Map<String, String> seedSuppliers() {
        seedService.generateSuppliers();
        HashMap<String, String> response = new HashMap<String, String>();
        response.put("message", "Seeding completed");
        response.put("data", "Seeding completed");
        response.put("successful", "true");
        return response;
    }
    @GetMapping("/products")
    public Map<String, String> seedProducts() {
        seedService.addSeedProducts();
        HashMap<String, String> response = new HashMap<String,String>();
        response.put("message", "Seeding completed");
        response.put("data", "Seeding completed");
        response.put("successful", "true");
        return response;
    }
    @GetMapping("/users")
    public Map<String, String> seedUsers() {
        seedService.generateUsers();
        HashMap<String, String> response = new HashMap<String,String>();
        response.put("message", "Seeding completed");
        response.put("data", "Seeding completed");
        response.put("successful", "true");
        return response;
    }
    @GetMapping("/orders/{amount}")
    public Map<String, String> seedOrders(@PathVariable int amount) {
        seedService.generateOrders(amount);
        HashMap<String, String> response = new HashMap<String,String>();
        response.put("message", "Seeding completed");
        response.put("data", "Seeding completed");
        response.put("successful", "true");
        return response;
    }
    @GetMapping("/sales/{amount}")
    public Map<String, String> seedSales(@PathVariable int amount) {
        seedService.generateSales(amount);
        HashMap<String, String> response = new HashMap<String,String>();
        response.put("message", "Seeding completed");
        response.put("data", "Seeding completed");
        response.put("successful", "true");
        return response;
    }
}
