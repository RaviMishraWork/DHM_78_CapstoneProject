package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlertDTO {
    private int productSKU;
    private Integer quantity;
}
