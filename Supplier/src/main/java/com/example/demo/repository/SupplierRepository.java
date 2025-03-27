package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.SupplierEntity;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Integer> {
	Optional<SupplierEntity> findByName(String name);
}
