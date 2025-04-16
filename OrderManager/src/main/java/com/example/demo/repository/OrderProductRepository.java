package com.example.demo.repository;

import com.example.demo.data.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {
//    List<OrderProduct> findAllByOrderId(UUID orderId);
    List<OrderProduct> findAllByOrderOrderId(UUID orderId);

    List<OrderProduct> findAllBySku(int sku);
}
