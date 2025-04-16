package com.example.demo;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryStockService {
    @Autowired
    private InventoryStockRepository inventoryStockRepository;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserClient userClient;
    @Autowired
    private SaleClient saleClient;

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
    @Transactional(rollbackOn = Throwable.class)
    public List<InventoryStockDao> decreaseStockForSkus(Map<Integer, Integer> skusAndQuantities) throws Exception {
        List<InventoryStockEntity> entities = inventoryStockRepository.findAllById(skusAndQuantities.keySet());
        for (InventoryStockEntity entity : entities) {
            int sku = entity.getSku();
            int quantityToDecrease = skusAndQuantities.get(sku);
            if (entity.getQuantity() >= quantityToDecrease) {
                int newQuantity = entity.getQuantity() - quantityToDecrease;
                entity.setQuantity(newQuantity);
            }
            else {
                throw new Exception("Insufficient stock for SKU: " + sku);
            }
        }
        inventoryStockRepository.saveAll(entities);
        return entities.stream().map(InventoryStockDao::new).collect(Collectors.toList());
    }
    public InventoryStockDao increaseQuantityBySku(int sku, int quantity) {
        InventoryStockEntity entity = inventoryStockRepository.findBySku(sku).orElse(null);
        if (entity != null) {
            int newQuantity = entity.getQuantity() + quantity;
            entity.setQuantity(newQuantity);
            AlertEntity alertForSku = alertRepository.findBySku(sku);
            if (alertForSku != null) {
                alertForSku.setQuantity(newQuantity);
                alertForSku =  alertRepository.save(alertForSku);
                if (alertForSku.getQuantity() >= alertForSku.getThreshold()) {
                    deleteAlert(sku);
                }
            }
            return new InventoryStockDao(inventoryStockRepository.save(entity));
        }
        return null;
    }
    @Transactional(rollbackOn = Throwable.class)
    public List<InventoryStockDao> increaseStockForSkus(Map<Integer, Integer> skusAndQuantities) {
        List<InventoryStockEntity> entities = inventoryStockRepository.findAllBySkus(skusAndQuantities.keySet());
        for (InventoryStockEntity entity : entities) {
            int sku = entity.getSku();
            int quantityToIncrease = skusAndQuantities.get(sku);
            int newQuantity = entity.getQuantity() + quantityToIncrease;
            entity.setQuantity(newQuantity);
        }
        inventoryStockRepository.saveAll(entities);
        return entities.stream().map(InventoryStockDao::new).collect(Collectors.toList());
    }

//    public List<InventoryStockDao> getLowStockAlerts() {
//    	return inventoryStockRepository.findByQuantityLessThan(LOW_STOCK_THRESHOLD).stream().map(InventoryStockDao::new).collect(Collectors.toList());
//    }
    public List<AlertDao> getAllAlerts() {
        updateAlerts();
        checkPendingDemands();
        List<AlertEntity> entities = alertRepository.findAll();
        return entities.stream().map(this::mapAlertEntityToDao).collect(Collectors.toList());
    }
    public void updateAlerts() {
        List<InventoryStockEntity> lowStockEntities = inventoryStockRepository.findByQuantityLessThan(LOW_STOCK_THRESHOLD);
        for (InventoryStockEntity entity : lowStockEntities) {
            int sku = entity.getSku();
            int quantity = entity.getQuantity();
            String message = "Low stock alert for SKU: " + sku + ". Current quantity: " + quantity;
            addAlert(sku, message, quantity, LOW_STOCK_THRESHOLD);
        }
    }
    public void checkPendingDemands() {
        Map<Integer,Integer> skusAndQuantities = saleClient.getPendingDemands();
        System.out.println(skusAndQuantities);
        List<InventoryStockEntity> entities = inventoryStockRepository.findAllBySkus(skusAndQuantities.keySet());
        for (InventoryStockEntity entity : entities) {
            System.out.println(entity);
            int sku = entity.getSku();
            int quantityToDecrease = skusAndQuantities.get(sku);
            System.out.println("quantityToDecrease: " + quantityToDecrease);
            System.out.println("entity.getQuantity(): " + entity.getQuantity());
            if (entity.getQuantity() < quantityToDecrease) {
                addAlert(sku, "The demands of pending orders have exceeded the available stock for SKU: " + sku + ". Please restock.", entity.getQuantity(), quantityToDecrease);
            }
        }

    }

    public Map<Integer,Integer> handlePendingDemands(Map<Integer,Integer> skusAndQuantities) {
    	List<InventoryStockEntity> entities = inventoryStockRepository.findAllBySkus(skusAndQuantities.keySet());
    	Map<Integer,Integer> retMap = new HashMap<Integer,Integer>();
        for (InventoryStockEntity entity : entities) {
    		int sku = entity.getSku();
    		int quantityToDecrease = skusAndQuantities.get(sku);
    		if (entity.getQuantity() < quantityToDecrease) {
                addAlert(sku, "The demands of pending orders have exceeded the available stock for SKU: " + sku + ". Please restock.", entity.getQuantity(), quantityToDecrease);
                retMap.put(sku, entity.getQuantity() - quantityToDecrease);
            }
    	}
//    	inventoryStockRepository.saveAll(entities);
        	return retMap;
    	//return entities.stream().map(InventoryStockDao::new).collect(Collectors.toList());
    }

    public void addAlert(int sku, String message, int quantity, int threshold) {
        AlertEntity existingAlert = alertRepository.findBySku(sku);
        if (existingAlert != null) {
            existingAlert.setMessage(message);
            existingAlert.setQuantity(quantity);
            existingAlert.setThreshold(threshold);
            existingAlert= alertRepository.save(existingAlert);
//            sendAlert(existingAlert);
            return;
        } else {
            // Create a new alert
            AlertEntity alert = new AlertEntity();
            alert.setSku(sku);
            alert.setMessage(message);
            alert.setQuantity(quantity);
            alert.setThreshold(threshold);
            alert = alertRepository.save(alert);
            sendAlert(alert);

        }
    }
    public void sendAlert(AlertEntity alert) {
        String EmailTemplateHTML = "<html><body style=\"text-align:center;\"><h1>IMS:Inventory Management System</h1><h2>Alert for SKU: %d</h2><h3>Created at: %s</h3><h3>Updated at: %s</h3><p>%s</p></body></html>";
        String emailContent = String.format(EmailTemplateHTML, alert.getSku(), alert.getCreatedAt().toString(), alert.getUpdatedAt().toString(), alert.getMessage());
        List<String> admins = userClient.getAllAlertAdmins();
        String adminsString = admins.stream().collect(Collectors.joining(","));
        String subject = "IMS: Inventory Management System - Alert for SKU: " + alert.getSku();
        emailService.sendMimeMessage("alerts.ims@m-m.institute", adminsString, subject, emailContent);
        System.out.println("Email Alert sent for SKU: " + alert.getSku() + " with message: " + alert.getMessage());
    }
    public void deleteAlert(int sku) {
        AlertEntity entity = alertRepository.findBySku(sku);
        if (entity != null) {
            alertRepository.delete(entity);
        }
    }
    public void deleteAllAlerts() {
        alertRepository.deleteAll();
    }
    public AlertEntity mapAlertDaoToEntity(AlertDao alertDao) {
        AlertEntity entity = new AlertEntity();
        entity.setSku(alertDao.getSku());
        entity.setMessage(alertDao.getMessage());
        entity.setQuantity(alertDao.getQuantity());
        entity.setCreatedAt(alertDao.getCreatedAt());
        entity.setUpdatedAt(alertDao.getUpdatedAt());
        entity.setThreshold(alertDao.getThreshold());
        return entity;
    }
    public AlertDao mapAlertEntityToDao(AlertEntity alertEntity) {
        AlertDao alertDao = new AlertDao();
        alertDao.setSku(alertEntity.getSku());
        alertDao.setMessage(alertEntity.getMessage());
        alertDao.setQuantity(alertEntity.getQuantity());
        alertDao.setCreatedAt(alertEntity.getCreatedAt());
        alertDao.setUpdatedAt(alertEntity.getUpdatedAt());
        alertDao.setThreshold(alertEntity.getThreshold());
        return alertDao;
    }
}
