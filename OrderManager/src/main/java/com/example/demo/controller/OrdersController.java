package com.example.demo.controller;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderRequestDTO;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public @ResponseBody OrderDTO createOrder(@RequestBody OrderDTO orderRequest) {
        System.out.println(orderRequest);
        return orderService.createOrder(orderRequest);
    }

    @GetMapping("/")
    public @ResponseBody  List<OrderDTO> getOrders() {
        return orderService.getAllOrder();
    }

    @GetMapping("/{orderId}")
    public OrderDTO getOrder(@PathVariable UUID orderId) {
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/{orderId}")
    public OrderDTO updateOrder(@PathVariable UUID orderId, @RequestBody OrderDTO orderRequest) {
        return orderService.updateOrder(orderId, orderRequest);
    }

    @DeleteMapping("/{orderId}")
    public OrderDTO deleteOrder(@PathVariable UUID orderId) {
        return orderService.deleteOrder(orderId);
    }

    @PostMapping("/mark-complete/{orderId}")
    public OrderDTO markOrderAsCompleted(@PathVariable UUID orderId) {
        return orderService.markOrderAsCompleted(orderId);
    }

    @GetMapping("/sku/{sku}")
    public List<OrderDTO> getOrdersBySku(@PathVariable Integer sku) {
        return orderService.getOrdersBySku(sku);
    }

    @GetMapping("/totalorders/sku")
    public Map<Integer, Integer> getTotalOrdersForEachSku() {
        return orderService.getTotalOrdersForEachSku();
    }
    @GetMapping("/totalexpenditures/sku")
    public Map<Integer, Double> getTotalExpendituresForEachSku() {
        return orderService.getTotalExpendituresForEachSku();
    }


}
