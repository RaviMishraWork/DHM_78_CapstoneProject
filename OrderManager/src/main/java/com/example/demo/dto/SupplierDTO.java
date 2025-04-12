package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierDTO {
    private int id;
    private String name;
    private String contactInfo;
    private String rating;
}
