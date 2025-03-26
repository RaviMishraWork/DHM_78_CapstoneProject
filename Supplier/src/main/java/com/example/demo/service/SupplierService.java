package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.SupplierDao;
import com.example.demo.entity.SupplierEntity;
import com.example.demo.repository.SupplierRepository;

import jakarta.ws.rs.sse.Sse;


@Service
public class SupplierService {
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	private SupplierDao convertToDao(SupplierEntity supplierEntity) {
		return SupplierDao.builder()
				.contactInfo(supplierEntity.getContactInfo())
				.rating(supplierEntity.getRating())
				.build();
	}
	
	private SupplierEntity convertToEntity(SupplierDao supploerDao) {
		return SupplierEntity.builder()
				.contactInfo(supploerDao.getContactInfo())
				.rating(supploerDao.getRating())
				.build();
	}
	
	public SupplierDao insertSupplier(SupplierDao supplierDao) {
		SupplierEntity se = convertToEntity(supplierDao);
		supplierRepo.save(se);
		return convertToDao(se);
	}
	
	public List<SupplierDao> getAllSuppliers() {
		return supplierRepo.findAll().stream()
				.map((se) -> {
					return convertToDao(se);
				}).collect(Collectors.toList());
	}
	
	public SupplierDao getSupplierById(int id) {
		return convertToDao(supplierRepo.findById(id).orElse(null));
	}

	public SupplierDao updateSupplier(int id, SupplierDao supplierDao) {
		SupplierEntity se = supplierRepo.findById(id).orElse(null);
		
		if (se != null) {
			se = convertToEntity(supplierDao);
			se = supplierRepo.save(se);
			return convertToDao(se);
		} else {
			return null;
		}
	}
	
	public SupplierDao deleteSupplier(int id) {
		SupplierEntity se = supplierRepo.findById(id).orElse(null);
		
		if (se != null) {
			supplierRepo.delete(se);
			return convertToDao(se);
		} else {
			return null;
		}
		
		
	}
	
	

}
