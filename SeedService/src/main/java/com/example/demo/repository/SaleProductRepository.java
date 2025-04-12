package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.data.SaleProduct;

import java.util.List;
import java.util.UUID;

@Repository
public interface SaleProductRepository extends JpaRepository<SaleProduct, Integer> {
//    List<SaleProduct> findAllBySaleId(UUID saleId);
}
