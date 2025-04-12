package com.example.demo.repository;

import com.example.demo.data.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {
//    List<OrderProduct> findAllByOrderId(UUID orderId);
}
