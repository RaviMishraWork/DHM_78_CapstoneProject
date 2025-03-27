package com.example.demo.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "supplier_id")
    private int supplierId;
}
