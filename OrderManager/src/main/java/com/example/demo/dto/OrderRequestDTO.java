package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
@Builder
public class OrderRequestDTO {
    private int supplier_id;
    private List<ProductDTO> product_list;
}
