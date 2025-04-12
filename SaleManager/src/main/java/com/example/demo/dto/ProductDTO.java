package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
    private Integer sku;
    private Integer quantity;
    private String name;
    private double price;
}
