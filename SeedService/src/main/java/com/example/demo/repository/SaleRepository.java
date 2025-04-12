package com.example.demo.repository;

import com.example.demo.data.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SaleRepository extends JpaRepository<Sale, UUID> {
//	    List<Sale> findAll();
    List<Sale> findByCustomerUsername(String customerUsername);
}
