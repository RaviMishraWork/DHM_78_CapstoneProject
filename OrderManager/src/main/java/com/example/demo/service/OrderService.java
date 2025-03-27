package com.example.demo.service;

import com.example.demo.data.Order;
import com.example.demo.data.OrderProduct;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderRequestDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.SupplierDTO;
import com.example.demo.repository.OrderProductRepository;
import com.example.demo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Transactional
    public OrderDTO createOrder(OrderRequestDTO OrderRequest) {
        Order order = Order.builder()
                .supplierId(OrderRequest.getSupplier_id())
                .build();

        order = orderRepository.save(order);

        HashMap<Integer, Integer> productSkusAndQuantities = OrderRequest.getProductSkusAndQuantities();

        Order finalOrder = order;
        productSkusAndQuantities.forEach((sku, quantity) -> {
            OrderProduct orderProduct = OrderProduct.builder()
                    .orderId(finalOrder.getOrderId())
                    .productId(sku)
                    .productQuantity(quantity)
                    .build();
            orderProductRepository.save(orderProduct);
        });

        return convertOrderToOrderDTO(finalOrder);
    }

    public OrderDTO getOrderById(UUID orderId) {
        Order order = orderRepository.getReferenceById(orderId);
        return convertOrderToOrderDTO(order);
    }

    public List<OrderDTO> getAllOrder() {
        List<Order> orderList = orderRepository.getAll();
        return orderList.stream().map(this::convertOrderToOrderDTO).collect(Collectors.toList());
    }

    public OrderDTO updateOrder(UUID orderId, OrderRequestDTO OrderRequest) {
        Order order = orderRepository.getReferenceById(orderId);
        order = orderRepository.save(order);
        OrderDTO orderDTO = convertOrderToOrderDTO(order);
        List<OrderProduct> orderProducts = orderProductRepository.findAllByOrderId(order.getOrderId());
        orderProductRepository.saveAll(orderProducts);
        return orderDTO;
    }

    public OrderDTO deleteOrder(UUID orderId) {
        Order order = orderRepository.getReferenceById(orderId);
        orderRepository.delete(order);

        OrderDTO orderDTO = convertOrderToOrderDTO(order);
        List<OrderProduct> orderProducts = orderProductRepository.findAllByOrderId(order.getOrderId());
        orderProductRepository.deleteAll(orderProducts);
        return orderDTO;
    }

    private OrderDTO convertOrderToOrderDTO(Order finalOrder) {
        List<OrderProduct> orderProducts = orderProductRepository.findAllByOrderId(finalOrder.getOrderId());

        List<ProductDTO> productDTOList = orderProducts.stream().map(orderProduct -> ProductDTO.builder()
                .productId(orderProduct.getProductId())
                .productQuantity(orderProduct.getProductQuantity())
                .build()).toList();

        SupplierDTO supplierDTO = SupplierDTO.builder().id(finalOrder.getSupplierId()).build();

        return OrderDTO.builder()
                .orderId(finalOrder.getOrderId())
                .productList(productDTOList)
                .supplier(supplierDTO)
                .build();
    }

}
