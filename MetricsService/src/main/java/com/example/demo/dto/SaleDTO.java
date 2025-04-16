package com.example.demo.dto;

import com.example.demo.data.SaleStatus;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@ToString
public class SaleDTO {
    private UUID saleId;
    private List<ProductDTO> productList;
    private Double totalPrice;
    private String customerUsername;
    private Date saleCreated;
    private Date saleUpdated;
    private Date saleCompleted;
    private SaleStatus saleStatus;
//    private SupplierDTO supplier;
}
