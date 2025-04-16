package com.example.demo.service;

import com.example.demo.client.ProductClient;
import com.example.demo.client.SupplierClient;
import com.example.demo.data.Order;
import com.example.demo.data.OrderProduct;
import com.example.demo.data.OrderStatus;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderRequestDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.SupplierDTO;
import com.example.demo.repository.OrderProductRepository;
import com.example.demo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private SupplierClient supplierClient;

    @Autowired
    private ProductClient productClient;

    @Transactional
    public OrderDTO createOrder(OrderDTO request) {
        List<ProductDTO> orderProducts = request.getProductList();
        List<OrderProduct> orderProductList = orderProducts.stream().map(productDTO -> OrderProduct.builder()
                .name(productDTO.getName())
                .sku(productDTO.getSku())
                .price(productDTO.getPrice())
                .quantity(productDTO.getQuantity())
                .build()).collect(Collectors.toList());
        Order newOrder = Order.builder()
                .supplierId(request.getSupplier().getId())
                .totalPrice(request.getTotalPrice())
                .products(new ArrayList<OrderProduct>())
                .build();
        orderProductList.stream().forEach(newOrder::addProduct);
        newOrder = orderRepository.save(newOrder);
//
//        HashMap<Integer, Integer> productSkusAndQuantities = OrderRequest.getProductSkusAndQuantities();
//
//        Order finalOrder = order;
//        productSkusAndQuantities.forEach((sku, quantity) -> {
//            OrderProduct orderProduct = OrderProduct.builder()
//                    .orderId(finalOrder.getOrderId())
//                    .productId(sku)
//                    .productQuantity(quantity)
//                    .build();
//            orderProductRepository.save(orderProduct);
//        });

        return convertOrderToOrderDTO(newOrder);
    }

    public OrderDTO getOrderById(UUID orderId) {
        Order order = orderRepository.getReferenceById(orderId);
        return convertOrderToOrderDTO(order);
    }
    @Transactional
    public List<OrderDTO> getOrdersBySku(Integer sku) {
            List<OrderProduct> orderProducts = orderProductRepository.findAllBySku(sku);
            List<Order> orderList = new ArrayList<Order>();
            orderProducts.stream().forEach(orderProduct -> {
                Order order = orderRepository.getReferenceById(orderProduct.getOrder().getOrderId());
                orderList.add(order);
            });
//        List<Order> orderList = orderRepository.findAllBySku(sku);
        return orderList.stream().map(this::convertOrderToOrderDTO).collect(Collectors.toList());
    }

    public Map<Integer,Integer> getTotalOrdersForEachSku() {
        List<OrderProduct> orderProducts = orderProductRepository.findAll();
        Map<Integer, Integer> skuQuantityMap = new HashMap<>();
        for (OrderProduct orderProduct : orderProducts) {
            int sku = orderProduct.getSku();
            int quantity = orderProduct.getQuantity();
            if (skuQuantityMap.containsKey(sku)) {
                quantity += skuQuantityMap.get(sku);
            }
            skuQuantityMap.put(sku, skuQuantityMap.getOrDefault(sku, 0) + quantity);
        }
        return skuQuantityMap;
    }
    public Map<Integer, Double> getTotalExpendituresForEachSku() {
        List<OrderProduct> orderProducts = orderProductRepository.findAll();
        Map<Integer, Double> skuExpenditureMap = new HashMap<>();
        for (OrderProduct orderProduct : orderProducts) {
            int sku = orderProduct.getSku();
            double price = orderProduct.getPrice();
            int quantity = orderProduct.getQuantity();
            if (skuExpenditureMap.containsKey(sku)) {
                price += skuExpenditureMap.get(sku);
            }
            skuExpenditureMap.put(sku, skuExpenditureMap.getOrDefault(sku, 0.0) + (price * quantity));
        }
        return skuExpenditureMap;
    }

//    public List<OrderDTO> getOrdersBySupplierId(Integer supplierId) {
//        List<Order> orderList = orderRepository.findAllBySupplierId(supplierId);
//        return orderList.stream().map(this::convertOrderToOrderDTO).collect(Collectors.toList());
//    }

    public List<OrderDTO> getAllOrder() {
        List<Order> orderList = orderRepository.findAll();
        return orderList.stream().map(this::convertOrderToOrderDTO).collect(Collectors.toList());
    }

    public OrderDTO updateOrder(UUID orderId, OrderDTO request) {
        Order order = orderRepository.getReferenceById(orderId);
        order.setSupplierId(request.getSupplier().getId());
        order.setTotalPrice(request.getTotalPrice());
        order.setProducts(request.getProductList().stream().map(productDTO -> OrderProduct.builder()
                .name(productDTO.getName())
                .sku(productDTO.getSku())
                .price(productDTO.getPrice())
                .quantity(productDTO.getQuantity())
                .build()).collect(Collectors.toList()));
        order = orderRepository.save(order);
        OrderDTO orderDTO = convertOrderToOrderDTO(order);
//        List<OrderProduct> orderProducts = orderProductRepository.findAllByOrderId(order.getOrderId());
//        orderProductRepository.saveAll(orderProducts);
        return orderDTO;
    }

    public OrderDTO deleteOrder(UUID orderId) {
        Order order = orderRepository.getReferenceById(orderId);
        orderRepository.delete(order);

        OrderDTO orderDTO = convertOrderToOrderDTO(order);
//        List<OrderProduct> orderProducts = orderProductRepository.findAllByOrderId(order.getOrderId());
//        orderProductRepository.deleteAll(orderProducts);
        return orderDTO;
    }

    public OrderDTO markOrderAsCompleted(UUID orderId) {
        Order order = orderRepository.getReferenceById(orderId);
        order.setOrderStatus(OrderStatus.COMPLETED);
        order.setOrderCompleted(new java.sql.Date(System.currentTimeMillis()));
        List<OrderProduct> products = order.getProducts();
        products.stream().forEach(
                orderProduct -> {
                    productClient.increaseProduct(orderProduct.getSku(), orderProduct.getQuantity());
                }
        );
        order = orderRepository.save(order);
        return convertOrderToOrderDTO(order);
    }

    private OrderDTO convertOrderToOrderDTO(Order finalOrder) {
        List<OrderProduct> orderProducts = finalOrder.getProducts();

        List<ProductDTO> productDTOList = orderProducts.stream().map(orderProduct -> ProductDTO.builder()
                .name(orderProduct.getName())
                .sku(orderProduct.getSku())
                .price(orderProduct.getPrice())
                .quantity(orderProduct.getQuantity())
                .build()).toList();

        SupplierDTO supplierDTO = supplierClient.getSupplierById(finalOrder.getSupplierId());  //SupplierDTO.builder().id(finalOrder.getSupplierId()).build();

        return OrderDTO.builder()
                .orderId(finalOrder.getOrderId())
                .productList(productDTOList)
                .totalPrice(finalOrder.getTotalPrice())
                .supplier(supplierDTO)
                .orderCreated(finalOrder.getOrderCreated())
                .orderUpdated(finalOrder.getOrderUpdated())
                .orderCompleted(finalOrder.getOrderCompleted())
                .orderStatus(finalOrder.getOrderStatus())
                .build();
    }

}
