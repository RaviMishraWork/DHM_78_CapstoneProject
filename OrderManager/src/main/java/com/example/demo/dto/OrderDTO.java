package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Data
@Builder
@ToString
public class OrderDTO {
    private String orderId;
    private Set<ProductDTO> productList;
    private Double totalPrice;
    private SupplierDTO supplier;
}
