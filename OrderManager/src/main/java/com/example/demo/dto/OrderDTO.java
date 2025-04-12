package com.example.demo.dto;

import com.example.demo.data.OrderStatus;
import lombok.*;

import java.util.Date;
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
    private Date orderCreated;
    private Date orderUpdated;
    private Date orderCompleted;
    private OrderStatus orderStatus;
}
