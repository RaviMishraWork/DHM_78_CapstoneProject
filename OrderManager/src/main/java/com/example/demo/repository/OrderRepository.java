package com.example.demo.repository;

import com.example.demo.data.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
//    List<Order> findAllBySupplierId(Integer supplierId);
//	    List<Order> findAll();
//    @Query("SELECT order FROM Order order WHERE order.id IN (SELECT id FROM Order o RIGHT JOIN OrderProduct op ON o.id = op.order WHERE op.sku = ?1)")
//    List<Order> findAllBySku(int sku);
}
