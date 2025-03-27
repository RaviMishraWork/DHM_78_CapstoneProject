package com.example.demo.controller;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderRequestDTO;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public OrderDTO createOrder(@RequestBody OrderRequestDTO orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @GetMapping()
    public List<OrderDTO> getOrders() {
        return orderService.getAllOrder();
    }

    @GetMapping("/{orderId}")
    public OrderDTO getOrder(@PathVariable UUID orderId) {
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/{orderId}")
    public OrderDTO updateOrder(@PathVariable UUID orderId, @RequestBody OrderRequestDTO orderRequest) {
        return orderService.updateOrder(orderId, orderRequest);
    }

    @DeleteMapping("{orderId}")
    public OrderDTO deleteOrder(@PathVariable UUID orderId) {
        return orderService.deleteOrder(orderId);
    }
}
