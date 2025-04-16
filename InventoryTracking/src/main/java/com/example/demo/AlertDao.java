package com.example.demo;

import lombok.*;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class AlertDao {
    private int sku;
    private int threshold;
    private int quantity;
    private String message;
    private Date createdAt;
    private Date updatedAt;
}
