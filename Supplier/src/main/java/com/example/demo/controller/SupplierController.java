package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.SupplierDao;
import com.example.demo.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
	
	@Autowired
	SupplierService supplierService;
	
	@PostMapping("/insert")
	public SupplierDao insert(@RequestBody SupplierDao supplierDao) {
		return supplierService.insertSupplier(supplierDao);
	}
	
	@GetMapping
	public List<SupplierDao> getAllSuppliers() {
		return supplierService.getAllSuppliers();
	}
	
	@GetMapping("/{id}")
	public SupplierDao getAllSuppliers(@PathVariable int id) {
		return supplierService.getSupplierById(id);
	}
	
	@PutMapping("/{id}")
	public SupplierDao updateSupplier(@PathVariable int id, @RequestBody SupplierDao supplierDao) {
		return supplierService.updateSupplier(id, supplierDao);
	}
	
	@DeleteMapping("/{id")
	public SupplierDao deleteSupplier(@PathVariable int id) {
		return supplierService.deleteSupplier(id);
	}

}
