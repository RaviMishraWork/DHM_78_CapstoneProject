package com.example.demo.repository;

import com.example.demo.data.InventoryStockEntity;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface InventoryStockRepository extends JpaRepository<InventoryStockEntity, Integer> {

//    @Query(value = "SELECT * FROM inventory_stock WHERE sku = ?1", nativeQuery = true)
    Optional<InventoryStockEntity> findBySku(int sku);

    @Query(value = "SELECT * FROM inventory WHERE quantity < ?1", nativeQuery = true)
    List<InventoryStockEntity> findByQuantityLessThan(int quantity);
}
