package com.example.demo.controller;

import com.example.demo.data.Order;
import com.example.demo.data.OrderRequest;
import com.example.demo.dto.OrderDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @PostMapping("/place")
    public String createOrder(@RequestBody OrderRequest orderRequest) {
        // Todo
        return "success";
    }

    @GetMapping()
    public List<OrderDTO> getOrders() {
        // Todo
        return null;
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable String orderId) {
        return null;
    }
}
