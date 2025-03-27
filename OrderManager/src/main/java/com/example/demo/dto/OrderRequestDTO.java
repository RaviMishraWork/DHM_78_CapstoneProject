package com.example.demo.dto;

import java.util.HashMap;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRequestDTO {
	private int supplier_id;
	private HashMap<Integer,Integer> productSkusAndQuantities;
}
