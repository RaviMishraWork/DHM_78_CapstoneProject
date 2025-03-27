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

import com.example.demo.dto.SupplierDTO;
import com.example.demo.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
	
	@Autowired
	SupplierService supplierService;
	
	@PostMapping("/insert")
	public SupplierDTO insert(@RequestBody SupplierDTO supplierDao) {
		return supplierService.insertSupplier(supplierDao);
	}
	
	@GetMapping
	public List<SupplierDTO> getAllSuppliers() {
		return supplierService.getAllSuppliers();
	}
	
	@GetMapping("/id/{id}")
	public SupplierDTO getSupplierById(@PathVariable int id) {
		return supplierService.getSupplierById(id);
	}
	
	@GetMapping("/name/{name}")
	public SupplierDTO getSupplierByName(@PathVariable String name) {
		return supplierService.getSupplierByName(name);
	}
	
	@PutMapping("/update/{id}")
	public SupplierDTO updateSupplier(@PathVariable int id, @RequestBody SupplierDTO supplierDao) {
		return supplierService.updateSupplier(id, supplierDao);
	}
	
	@DeleteMapping("/delete/{id}")
	public SupplierDTO deleteSupplier(@PathVariable int id) {
		return supplierService.deleteSupplier(id);
	}

}
