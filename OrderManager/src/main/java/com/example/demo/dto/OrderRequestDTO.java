package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
public class OrderRequestDTO {
    private int supplier_id;
    private HashMap<Integer, Integer> productSkusAndQuantities;
}
