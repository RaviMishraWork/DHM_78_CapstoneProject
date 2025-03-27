package com.example.demo.service;

import com.example.demo.data.OrderProduct;
import com.example.demo.dto.OrderProductDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<ProductDTO> findByOrderId(UUID orderId) {
        List<OrderProduct> orderProductList = orderProductRepository.findAllByOrderId(orderId);

        return orderProductList.stream().map(orderProduct ->
                ProductDTO.builder()
                        .productId(orderProduct.getProductId())
                        .productQuantity(orderProduct.getProductQuantity())
                        .build()).collect(Collectors.toList());
    }
}
