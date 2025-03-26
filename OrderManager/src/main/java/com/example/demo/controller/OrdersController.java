package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @PostMapping("/place")
    public String createOrder(@RequestBody Order order) {
        // Todo
        return "success";
    }

    @GetMapping()
    public List<Order> getOrders() {
        // Todo
        return null;
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable String orderId) {
        return null;
    }
}
