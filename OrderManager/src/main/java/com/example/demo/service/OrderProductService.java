package com.example.demo.service;

import com.example.demo.OrderProductRepository;
import com.example.demo.data.OrderProduct;
import com.example.demo.data.OrderRequest;
import com.example.demo.dto.OrderProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProductService {

    @Autowired
    private OrderProductRepository orderProductRepository;

    public OrderProduct createOrderProduct(OrderProductDTO orderProductDTO) {
        OrderProduct orderProduct = OrderProduct.builder()
                .productId(orderProductDTO.getProductId())
                .productQuantity(orderProductDTO.getProductQuantity())
                .build();
        orderProductRepository.save(orderProduct);
        return orderProduct;
    }

    public List<OrderProduct> findAll() {
        return orderProductRepository.findAll();
    }
}
