package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderRequest;
import com.example.demo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    public OrderDTO convertToDto(OrderRequest order) {
    	
    }

    @Transactional
    public OrderDTO createOrder(OrderRequest orderRequest) {

    }
}
