package com.example.demo;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductClient productClient;
	
	@Autowired
	private OrderClient orderClient;
	
	public ProductDao mapEntityToDao(ProductEntity productEntity) {
		
		return ProductDao.builder()
				.sku(productEntity.getSku())
				.name(productEntity.getName())
				.description(productEntity.getDescription())
				.initial_stock(productEntity.getInitial_stock())
				.buyPrice(productEntity.getBuyPrice())
				.sellPrice(productEntity.getSellPrice())
				.build();
	}
	
	public ProductEntity mapDaoToEntity(ProductDao productDao) {
			
			return ProductEntity.builder()
					.sku(productDao.getSku())
					.name(productDao.getName())
					.description(productDao.getDescription())
					.initial_stock(productDao.getInitial_stock())
					.buyPrice(productDao.getBuyPrice())
					.sellPrice(productDao.getSellPrice())
					.build();			
	}
	//a.Add Product
	public ProductDao addProduct(ProductDao productDao) {
		ProductEntity productEntity = mapDaoToEntity(productDao);
		productEntity = productRepository.save(productEntity);// save data into table
		
		//product client adding product to inventory
		InventoryStockDao inventoryStockDao = 
				InventoryStockDao.builder()
				.sku(productEntity.getSku())
				.quantity(productEntity.getInitial_stock())
				.build();
		productClient.addToInventory(inventoryStockDao);
		
		return mapEntityToDao(productEntity);
	}
	
	//b.Update Product Details
	public ProductDao updateProduct(int id, ProductDao productDao) {
		ProductEntity productEntity = productRepository.findById(id).orElse(null);
		if(productEntity != null) {
			productEntity.setName(productDao.getName());
			productEntity.setDescription(productDao.getDescription());
			productEntity.setInitial_stock(productDao.getInitial_stock());
			
			productEntity = productRepository.save(productEntity);// save data into table
			return mapEntityToDao(productEntity);
		} else return null;
	}
	
	//c.Delete Product
	public ProductDao deleteProduct(int id) {
		ProductEntity productEntity = productRepository.findById(id).orElse(null);
		if (productEntity != null) {
			productRepository.delete(productEntity);
			//product client deleting product from inventory by sku
			productClient.deleteFromInventory(productEntity.getSku());
			return mapEntityToDao(productEntity);
		} else return null;
	}
	
	//d. View Product Details
	public ProductDao getProductDetails(int id) {
		ProductEntity productEntity = productRepository.findById(id).orElse(null);
		return mapEntityToDao(productEntity);
	}
	
	public List<ProductDao> getAllProductDetails() {
		List<ProductEntity> productEntities = productRepository.findAll();
		return productEntities.stream().map(this::mapEntityToDao).collect(Collectors.toList());
	}

	public ProductDao deleteProductBySku(int sku) {
		ProductEntity productEntity = productRepository.findBySku(sku);
		if (productEntity != null) {
			productRepository.delete(productEntity);
			productClient.deleteFromInventory(productEntity.getSku());
			return mapEntityToDao(productEntity);
		} else return null;
	}

	public ProductDao getProductDetailsBySku(int sku) {
		ProductEntity productEntity = productRepository.findBySku(sku);
		return mapEntityToDao(productEntity);
	}

	public ProductDao updateProductBySku(int sku, ProductDao productDao) {
		ProductEntity productEntity = productRepository.findBySku(sku);
		if(productEntity != null) {
			productEntity.setSku(productDao.getSku());
			productEntity.setName(productDao.getName());
			productEntity.setDescription(productDao.getDescription());
			productEntity.setBuyPrice(productDao.getBuyPrice());
			productEntity.setSellPrice(productDao.getSellPrice());
			productEntity.setInitial_stock(productDao.getInitial_stock());

			productEntity = productRepository.save(productEntity);
			return mapEntityToDao(productEntity);
		} else return null;
	}

	///api/v1/inventory/alerts for viewing low-stock alerts
	public List<InventoryStockDao> getAlerts(){
		return productClient.getAlerts();
	}
	
	//api/v1/inventory/orders/place to place order 
	public OrderDTO sendOrder(OrderDTO orderRequestDTO) {
		return orderClient.sendOrder(orderRequestDTO);
	}	
}


