package com.example.demo;

import lombok.*;

import java.util.List;
import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
    private UUID orderId;
    private List<ProductDTO> productList;
    private Double totalPrice;
    private SupplierDTO supplier;
}
