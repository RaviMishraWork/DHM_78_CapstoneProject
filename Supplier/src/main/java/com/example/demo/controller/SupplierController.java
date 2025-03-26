package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.SupplierDao;
import com.example.demo.entity.SupplierEntity;
import com.example.demo.service.SupplierService;

@RestController
public class SupplierController {
	
	@Autowired
	SupplierService supplierService;
	
	@PostMapping("/insert")
	SupplierEntity insert(@RequestBody SupplierDao supplierDao) {
		return supplierService.insertSupplier(supplierDao);
	}
	
	@GetMapping
	List<SupplierEntity> getAllSuppliers() {
		return supplierService.getAllSuppliers();
	}
	

}
