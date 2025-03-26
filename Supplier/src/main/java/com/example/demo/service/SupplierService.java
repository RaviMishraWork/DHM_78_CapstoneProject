package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.SupplierDao;
import com.example.demo.entity.SupplierEntity;
import com.example.demo.repository.SupplierRepository;

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
		SupplierEntity supplierEntity = convertToEntity(supplierDao);
		supplierRepo.save(supplierEntity );
		return convertToDao(supplierEntity );
	}
	
	public List<SupplierDao> getAllSuppliers() {
		return supplierRepo.findAll().stream()
				.map((supplierEntity ) -> {
					return convertToDao(supplierEntity );
				}).collect(Collectors.toList());
	}
	
	public SupplierDao getSupplierById(int id) {
		return convertToDao(supplierRepo.findById(id).orElse(null));
	}

	public SupplierDao updateSupplier(int id, SupplierDao supplierDao) {
		SupplierEntity supplierEntity = supplierRepo.findById(id).orElse(null);
		
		if (supplierEntity != null) {
			supplierEntity.setContactInfo(supplierDao.getContactInfo());
			supplierEntity.setRating(supplierDao.getRating());
			
			supplierEntity = supplierRepo.save(supplierEntity );
			return convertToDao(supplierEntity );
		} else {
			return null;
		}
	}
	
	public SupplierDao deleteSupplier(int id) {
		SupplierEntity supplierEntity  = supplierRepo.findById(id).orElse(null);
		
		if (supplierEntity  != null) {
			supplierRepo.delete(supplierEntity );
			return convertToDao(supplierEntity );
		} else {
			return null;
		}
		
		
	}
	
	

}
