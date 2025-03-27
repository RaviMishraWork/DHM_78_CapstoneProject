package com.example.demo;

import java.util.HashMap;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRequestDTO {
	private int supplier_id;
	private HashMap<Integer,Integer> productSkusAndQuantities;
}
