package com.example.demo.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class OrderProductDTO {
    private String orderId;
    private String productId;
    private Integer productQuantity;
}
