package com.example.demo.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private int sku;
    private String name;
    private String description;
    private int initial_stock;
    private double buyPrice = 0.0;
    private double sellPrice = 0.0;
    private int supplierId;
    private String imageUrl;
    private String category;
}

