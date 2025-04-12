package com.example.demo.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SaleProductDTO {
    private UUID saleId;
    private Integer productId;
    private Integer productQuantity;
}
