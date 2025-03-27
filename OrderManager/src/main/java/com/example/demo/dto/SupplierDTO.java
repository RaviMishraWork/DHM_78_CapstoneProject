package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierDTO {
    //    private String supplierId;
    private String supplierName;
    private String contactInfo;
    private String rating;
}
