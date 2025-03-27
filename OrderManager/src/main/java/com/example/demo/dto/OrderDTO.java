package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@ToString
public class OrderDTO {
    private UUID orderId;
    private List<ProductDTO> productList;
    private Double totalPrice;
    private SupplierDTO supplier;
}
