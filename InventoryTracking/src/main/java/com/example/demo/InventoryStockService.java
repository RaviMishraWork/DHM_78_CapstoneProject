package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryStockService {
    @Autowired
    private InventoryStockRepository inventoryStockRepository;
    private final int LOW_STOCK_THRESHOLD = 3;

    public List<InventoryStockDao> getAllInventoryStock() {
        List<InventoryStockEntity> entities = inventoryStockRepository.findAll();
        return entities.stream().map(InventoryStockDao::new).collect(Collectors.toList());
    }
    public InventoryStockDao getInventoryStockById(int id) {
        InventoryStockEntity entity =  inventoryStockRepository.findById(id).orElse(null);
        if (entity != null) {
            return new InventoryStockDao(entity);
        }
        return null;
    }
    public InventoryStockDao getInventoryStockBySku(int sku) {
        InventoryStockEntity entity =  inventoryStockRepository.findBySku(sku).orElse(null);
        if (entity != null) {
            return new InventoryStockDao(entity);
        }
        return null;
    }
    public InventoryStockDao addInventoryStock(InventoryStockDao inventoryStockDao) {
        InventoryStockEntity entity = new InventoryStockEntity(inventoryStockDao);
        return new InventoryStockDao(inventoryStockRepository.save(entity));
    }
    public InventoryStockDao updateInventoryStockById(int id, InventoryStockDao inventoryStockDao) {
        InventoryStockEntity entity = inventoryStockRepository.findById(id).orElse(null);
        if (entity != null) {
            entity.setSku(inventoryStockDao.getSku());
            entity.setQuantity(inventoryStockDao.getQuantity());
            return new InventoryStockDao(inventoryStockRepository.save(entity));
        }
        return null;
    }
    public InventoryStockDao updateInventoryStockBySku(int sku, InventoryStockDao inventoryStockDao) {
        InventoryStockEntity entity = inventoryStockRepository.findBySku(sku).orElse(null);
        if (entity != null) {
            entity.setSku(inventoryStockDao.getSku());
            entity.setQuantity(inventoryStockDao.getQuantity());
            return new InventoryStockDao(inventoryStockRepository.save(entity));
        }
        return null;
    }
    public InventoryStockDao deleteInventoryStockById(int id) {
        InventoryStockEntity entity = inventoryStockRepository.findById(id).orElse(null);
        if (entity != null) {
            inventoryStockRepository.delete(entity);
            return new InventoryStockDao(entity);
        }
        return null;
    }
    public InventoryStockDao deleteInventoryStockBySku(int sku) {
        InventoryStockEntity entity = inventoryStockRepository.findBySku(sku).orElse(null);
        if (entity != null) {
            inventoryStockRepository.delete(entity);
            return new InventoryStockDao(entity);
        }
        return null;
    }
    public InventoryStockDao updateInventoryStockQuantityBySku(int sku, int quantity) {
        InventoryStockEntity entity = inventoryStockRepository.findBySku(sku).orElse(null);
        if (entity != null) {
            entity.setQuantity(quantity);
            return new InventoryStockDao(inventoryStockRepository.save(entity));
        }
        return null;
    }
    public InventoryStockDao updateInventoryStockQuantityById(int id, int quantity) {
        InventoryStockEntity entity = inventoryStockRepository.findById(id).orElse(null);
        if (entity != null) {
            entity.setQuantity(quantity);
            return new InventoryStockDao(inventoryStockRepository.save(entity));
        }
        return null;
    }
    public InventoryStockDao decreaseQuantityBySku(int sku, int quantity) {
        InventoryStockEntity entity = inventoryStockRepository.findBySku(sku).orElse(null);
        if (entity != null && entity.getQuantity() >= quantity) {
            int newQuantity = entity.getQuantity() - quantity;
            entity.setQuantity(newQuantity);
            return new InventoryStockDao(inventoryStockRepository.save(entity));
        }
        return null;
    }
    public InventoryStockDao increaseQuantityBySku(int sku, int quantity) {
        InventoryStockEntity entity = inventoryStockRepository.findBySku(sku).orElse(null);
        if (entity != null) {
            int newQuantity = entity.getQuantity() + quantity;
            entity.setQuantity(newQuantity);
            return new InventoryStockDao(inventoryStockRepository.save(entity));
        }
        return null;
    }
    public List<InventoryStockDao> getLowStockAlerts() {
    	return inventoryStockRepository.findByQuantityLessThan(LOW_STOCK_THRESHOLD).stream().map(InventoryStockDao::new).collect(Collectors.toList());
    }
}
