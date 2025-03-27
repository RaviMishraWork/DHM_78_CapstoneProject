package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.SupplierDTO;
import com.example.demo.entity.SupplierEntity;
import com.example.demo.repository.SupplierRepository;

@Service
public class SupplierService {
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	private SupplierDTO convertToDto(SupplierEntity supplierEntity) {
		return SupplierDTO.builder()
				.id(supplierEntity.getId())
				.name(supplierEntity.getName())
				.contactInfo(supplierEntity.getContactInfo())
				.rating(supplierEntity.getRating())
				.build();
	}
	
	private SupplierEntity convertToEntity(SupplierDTO supploerDao) {
		return SupplierEntity.builder()
				.name(supploerDao.getName())
				.contactInfo(supploerDao.getContactInfo())
				.rating(supploerDao.getRating())
				.build();
	}
	
	public SupplierDTO insertSupplier(SupplierDTO supplierDao) {
		SupplierEntity supplierEntity = convertToEntity(supplierDao);
		supplierRepo.save(supplierEntity );
		return convertToDto(supplierEntity );
	}
	
	public List<SupplierDTO> getAllSuppliers() {
		return supplierRepo.findAll().stream()
				.map((supplierEntity ) -> {
					return convertToDto(supplierEntity );
				}).collect(Collectors.toList());
	}
	
	public SupplierDTO getSupplierById(int id) {
		return convertToDto(supplierRepo.findById(id).orElse(null));
	}
	
	public SupplierDTO getSupplierByName(String name) {
		SupplierEntity se = supplierRepo.findByName(name).orElse(null);
		return convertToDto(se);
	}

	public SupplierDTO updateSupplier(int id, SupplierDTO supplierDao) {
		SupplierEntity supplierEntity = supplierRepo.findById(id).orElse(null);
		
		if (supplierEntity != null) {
			supplierEntity.setName(supplierDao.getName());
			supplierEntity.setContactInfo(supplierDao.getContactInfo());
			supplierEntity.setRating(supplierDao.getRating());
			
			supplierEntity = supplierRepo.save(supplierEntity );
			return convertToDto(supplierEntity );
		} else {
			return null;
		}
	}
	
	public SupplierDTO deleteSupplier(int id) {
		SupplierEntity supplierEntity  = supplierRepo.findById(id).orElse(null);
		
		if (supplierEntity  != null) {
			supplierRepo.delete(supplierEntity );
			return convertToDto(supplierEntity );
		} else {
			return null;
		}
		
		
	}

}
