package com.example.demo.service;

import com.example.demo.dto.SaleProductDTO;
import com.example.demo.data.SaleProduct;
import com.example.demo.dto.ProductDTO;
import com.example.demo.repository.SaleProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SaleProductService {

    @Autowired
    private SaleProductRepository saleProductRepository;

//    public SaleProduct createSaleProduct(SaleProductDTO saleProductDTO) {
//        SaleProduct saleProduct = SaleProduct.builder()
//                .productId(saleProductDTO.getProductId())
//                .productQuantity(saleProductDTO.getProductQuantity())
//                .build();
//        saleProductRepository.save(saleProduct);
//        return saleProduct;
//    }
//
//    public List<ProductDTO> findBySaleId(UUID saleId) {
//        List<SaleProduct> saleProductList = saleProductRepository.findAllBySaleId(saleId);
//
//        return saleProductList.stream().map(saleProduct ->
//                ProductDTO.builder()
//                        .productId(saleProduct.getProductId())
//                        .productQuantity(saleProduct.getProductQuantity())
//                        .build()).collect(Collectors.toList());
//    }
}
