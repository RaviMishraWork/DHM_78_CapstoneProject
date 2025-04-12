package com.example.demo.repository;

import com.example.demo.data.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{

    ProductEntity findBySku(int sku);
}
