package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.SupplierDTO;
import com.example.demo.entity.SupplierEntity;
import com.example.demo.repository.SupplierRepository;

@Service
public class SupplierService {
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	private SupplierDTO convertToDao(SupplierEntity supplierEntity) {
		return SupplierDTO.builder()
				.contactInfo(supplierEntity.getContactInfo())
				.rating(supplierEntity.getRating())
				.build();
	}
	
	private SupplierEntity convertToEntity(SupplierDTO supploerDao) {
		return SupplierEntity.builder()
				.contactInfo(supploerDao.getContactInfo())
				.rating(supploerDao.getRating())
				.build();
	}
	
	public SupplierDTO insertSupplier(SupplierDTO supplierDao) {
		SupplierEntity supplierEntity = convertToEntity(supplierDao);
		supplierRepo.save(supplierEntity );
		return convertToDao(supplierEntity );
	}
	
	public List<SupplierDTO> getAllSuppliers() {
		return supplierRepo.findAll().stream()
				.map((supplierEntity ) -> {
					return convertToDao(supplierEntity );
				}).collect(Collectors.toList());
	}
	
	public SupplierDTO getSupplierById(int id) {
		return convertToDao(supplierRepo.findById(id).orElse(null));
	}

	public SupplierDTO updateSupplier(int id, SupplierDTO supplierDao) {
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
	
	public SupplierDTO deleteSupplier(int id) {
		SupplierEntity supplierEntity  = supplierRepo.findById(id).orElse(null);
		
		if (supplierEntity  != null) {
			supplierRepo.delete(supplierEntity );
			return convertToDao(supplierEntity );
		} else {
			return null;
		}
		
		
	}
	
	

}
