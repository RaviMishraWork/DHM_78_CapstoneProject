package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
public class SaleRequestDTO {
//    private int supplier_id;
    private String customerUsername;
    private HashMap<Integer, Integer> productSkusAndQuantities;
}
