package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.SupplierDao;
import com.example.demo.entity.SupplierEntity;
import com.example.demo.repository.SupplierRepository;


@Service
public class SupplierService {
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	public SupplierEntity insertSupplier(SupplierDao supplierDao) {
		SupplierEntity se = SupplierEntity.builder()
				.contactInfo(supplierDao.getContactInfo())
				.rating(supplierDao.getRating())
				.build();
		return supplierRepo.save(se);
	}
	
	public List<SupplierEntity> getAllSuppliers() {
		List<SupplierEntity> ses = supplierRepo.findAll();
		return ses;
		
	}
	
	

}
