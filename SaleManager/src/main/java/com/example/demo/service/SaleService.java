package com.example.demo.service;

import com.example.demo.client.ProductClient;
import com.example.demo.data.SaleStatus;
import com.example.demo.data.Sale;
import com.example.demo.data.SaleProduct;
import com.example.demo.dto.SaleDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.SupplierDTO;
import com.example.demo.repository.SaleProductRepository;
import com.example.demo.repository.SaleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleProductRepository saleProductRepository;

    @Autowired
    private ProductClient productClient;

//    @Transactional
//    public SaleDTO createSale(SaleRequestDTO SaleRequest) {
//        Sale sale = Sale.builder()
//                .customerUsername(SaleRequest.getCustomerUsername())
//                .build();
//
//        sale = saleRepository.save(sale);
//
//        HashMap<Integer, Integer> productSkusAndQuantities = SaleRequest.getProductSkusAndQuantities();
//
//        Sale finalSale = sale;
//        productSkusAndQuantities.forEach((sku, quantity) -> {
//            SaleProduct saleProduct = SaleProduct.builder()
//                    .saleId(finalSale.getSaleId())
//                    .productId(sku)
//                    .productQuantity(quantity)
//                    .build();
//            saleProductRepository.save(saleProduct);
//        });
//
//        return convertSaleToSaleDTO(finalSale);
//    }
//
//    public SaleDTO getSaleById(UUID saleId) {
//        Sale sale = saleRepository.getReferenceById(saleId);
//        return convertSaleToSaleDTO(sale);
//    }
    public List<SaleDTO> getSalesByUsername(String username) {
    	return saleRepository.findByCustomerUsername(username).stream().map(this::convertSaleToSaleDTO).collect(Collectors.toList());
    }
//
//    public List<SaleDTO> getAllSale() {
//        List<Sale> saleList = saleRepository.findAll();
//        return saleList.stream().map(this::convertSaleToSaleDTO).collect(Collectors.toList());
//    }
//
//    public SaleDTO updateSale(UUID saleId, SaleRequestDTO SaleRequest) {
//        Sale sale = saleRepository.getReferenceById(saleId);
//        sale = saleRepository.save(sale);
//        SaleDTO saleDTO = convertSaleToSaleDTO(sale);
//        List<SaleProduct> saleProducts = saleProductRepository.findAllBySaleId(sale.getSaleId());
//        saleProductRepository.saveAll(saleProducts);
//        return saleDTO;
//    }
//
//    public SaleDTO deleteSale(UUID saleId) {
//        Sale sale = saleRepository.getReferenceById(saleId);
//        saleRepository.delete(sale);
//
//        SaleDTO saleDTO = convertSaleToSaleDTO(sale);
//        List<SaleProduct> saleProducts = saleProductRepository.findAllBySaleId(sale.getSaleId());
//        saleProductRepository.deleteAll(saleProducts);
//        return saleDTO;
//    }
//
//    private SaleDTO convertSaleToSaleDTO(Sale finalSale) {
//        List<SaleProduct> saleProducts = saleProductRepository.findAllBySaleId(finalSale.getSaleId());
//
//        List<ProductDTO> productDTOList = saleProducts.stream().map(saleProduct -> ProductDTO.builder()
//                .productId(saleProduct.getProductId())
//                .productQuantity(saleProduct.getProductQuantity())
//                .build()).toList();
//
////        SupplierDTO supplierDTO = SupplierDTO.builder().id(finalSale.getSupplierId()).build();
//
//        return SaleDTO.builder()
//                .saleId(finalSale.getSaleId())
//                .productList(productDTOList)
//                .customerUsername(finalSale.getCustomerUsername())
//                .build();
//    }

    @Transactional
    public SaleDTO createSale(SaleDTO request) {
        List<ProductDTO> saleProducts = request.getProductList();
        List<SaleProduct> saleProductList = saleProducts.stream().map(productDTO -> SaleProduct.builder()
                .name(productDTO.getName())
                .sku(productDTO.getSku())
                .price(productDTO.getPrice())
                .quantity(productDTO.getQuantity())
                .build()).collect(Collectors.toList());
        Sale newSale = Sale.builder()
                .customerUsername(request.getCustomerUsername())
                .totalPrice(request.getTotalPrice())
                .products(new ArrayList<SaleProduct>())
                .build();
        saleProductList.stream().forEach(newSale::addProduct);
        newSale = saleRepository.save(newSale);
        handlePendingDemands();
//
//        HashMap<Integer, Integer> productSkusAndQuantities = SaleRequest.getProductSkusAndQuantities();
//
//        Sale finalSale = sale;
//        productSkusAndQuantities.forEach((sku, quantity) -> {
//            SaleProduct saleProduct = SaleProduct.builder()
//                    .saleId(finalSale.getSaleId())
//                    .productId(sku)
//                    .productQuantity(quantity)
//                    .build();
//            saleProductRepository.save(saleProduct);
//        });

        return convertSaleToSaleDTO(newSale);
    }

    public SaleDTO getSaleById(UUID saleId) {
        Sale sale = saleRepository.getReferenceById(saleId);
        return convertSaleToSaleDTO(sale);
    }

    public List<SaleDTO> getAllSale() {
        List<Sale> saleList = saleRepository.findAll();
        return saleList.stream().map(this::convertSaleToSaleDTO).collect(Collectors.toList());
    }

    public SaleDTO updateSale(UUID saleId, SaleDTO request) {
        Sale sale = saleRepository.getReferenceById(saleId);
        sale.setCustomerUsername(request.getCustomerUsername());
        sale.setTotalPrice(request.getTotalPrice());
        sale.setProducts(request.getProductList().stream().map(productDTO -> SaleProduct.builder()
                .name(productDTO.getName())
                .sku(productDTO.getSku())
                .price(productDTO.getPrice())
                .quantity(productDTO.getQuantity())
                .build()).collect(Collectors.toList()));
        sale = saleRepository.save(sale);
        handlePendingDemands();
        SaleDTO saleDTO = convertSaleToSaleDTO(sale);
//        List<SaleProduct> saleProducts = saleProductRepository.findAllBySaleId(sale.getSaleId());
//        saleProductRepository.saveAll(saleProducts);
        return saleDTO;
    }

    public SaleDTO deleteSale(UUID saleId) {
        Sale sale = saleRepository.getReferenceById(saleId);
        saleRepository.delete(sale);

        SaleDTO saleDTO = convertSaleToSaleDTO(sale);
//        List<SaleProduct> saleProducts = saleProductRepository.findAllBySaleId(sale.getSaleId());
//        saleProductRepository.deleteAll(saleProducts);
        return saleDTO;
    }
    @Transactional(rollbackOn = Throwable.class)
    public SaleDTO markSaleAsShipped(UUID saleId) throws Exception {
        Sale sale = saleRepository.getReferenceById(saleId);
        sale.setSaleStatus(SaleStatus.SHIPPED);
        sale.setSaleCompleted(new java.sql.Date(System.currentTimeMillis()));
        sale = saleRepository.save(sale);
        List<SaleProduct> products = sale.getProducts();
        Map<Integer, Integer> skusAndQuantities = new java.util.HashMap<>();
        products.stream().forEach(
                saleProduct -> {
//                    productClient.decreaseProduct(saleProduct.getSku(), saleProduct.getQuantity());
                    skusAndQuantities.put(saleProduct.getSku(), saleProduct.getQuantity());
                }
        );
        Map<Integer,Integer> result =  productClient.decreaseProducts(skusAndQuantities);
        if (result.size() != skusAndQuantities.size()) {
            throw new Exception("Insufficient stock for one or more products");
        }
        return convertSaleToSaleDTO(sale);
    }

    public SaleDTO markSaleAsCompleted(UUID saleId) {
        Sale sale = saleRepository.getReferenceById(saleId);
        sale.setSaleStatus(SaleStatus.COMPLETED);
        sale.setSaleCompleted(new java.sql.Date(System.currentTimeMillis()));
        sale = saleRepository.save(sale);
        return convertSaleToSaleDTO(sale);
    }
    public SaleDTO markSaleAsCancelled(UUID saleId) {
        Sale sale = saleRepository.getReferenceById(saleId);
        sale.setSaleStatus(SaleStatus.CANCELLED);
        sale.setSaleCompleted(new java.sql.Date(System.currentTimeMillis()));
        sale = saleRepository.save(sale);
        return convertSaleToSaleDTO(sale);
    }

    private SaleDTO convertSaleToSaleDTO(Sale finalSale) {
        List<SaleProduct> saleProducts = finalSale.getProducts();

        List<ProductDTO> productDTOList = saleProducts.stream().map(saleProduct -> ProductDTO.builder()
                .name(saleProduct.getName())
                .sku(saleProduct.getSku())
                .price(saleProduct.getPrice())
                .quantity(saleProduct.getQuantity())
                .build()).toList();

        //SupplierDTO supplierDTO = supplierClient.getSupplierById(finalSale.getSupplierId());  //SupplierDTO.builder().id(finalSale.getSupplierId()).build();

        return SaleDTO.builder()
                .saleId(finalSale.getSaleId())
                .productList(productDTOList)
                .totalPrice(finalSale.getTotalPrice())
                .customerUsername(finalSale.getCustomerUsername())
                .saleCreated(finalSale.getSaleCreated())
                .saleUpdated(finalSale.getSaleUpdated())
                .saleCompleted(finalSale.getSaleCompleted())
                .saleStatus(finalSale.getSaleStatus())
                .build();
    }

    public List<SaleDTO> getSalesBySku(Integer sku) {
        List<SaleProduct> saleProducts = saleProductRepository.findAllBySku(sku);
        List<Sale> saleList = new ArrayList<Sale>();
        saleProducts.stream().forEach(saleProduct -> {
            Sale sale = saleRepository.getReferenceById(saleProduct.getSale().getSaleId());
            saleList.add(sale);
        });
//        List<Sale> saleList = saleRepository.findAllBySku(sku);
        return saleList.stream().map(this::convertSaleToSaleDTO).collect(Collectors.toList());
    }

    public Map<Integer, Integer> getPendingDemands()
    {
        List<Sale> saleList = saleRepository.findAllBySaleStatus(SaleStatus.PENDING);
        Map<Integer, Integer> skusAndQuantities = new java.util.HashMap<>();
        saleList.stream().forEach(sale -> {
            List<SaleProduct> saleProducts = sale.getProducts();
            saleProducts.stream().forEach(saleProduct -> {
                if (skusAndQuantities.containsKey(saleProduct.getSku())) {
                    skusAndQuantities.put(saleProduct.getSku(), skusAndQuantities.get(saleProduct.getSku()) + saleProduct.getQuantity());
                } else {
                    skusAndQuantities.put(saleProduct.getSku(), saleProduct.getQuantity());
                }
            });
        });
        return skusAndQuantities;
    }
    public void handlePendingDemands() {
        Map<Integer, Integer> skusAndQuantities = getPendingDemands();
        Map<Integer, Integer> result = productClient.handlePendingDemands(skusAndQuantities);
    }

}
