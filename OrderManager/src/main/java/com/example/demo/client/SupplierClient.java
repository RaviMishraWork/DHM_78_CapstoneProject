package com.example.demo.client;

import com.example.demo.dto.SupplierDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "supplier", url = "http://localhost:9090/supplier")
public interface SupplierClient {

    @GetMapping("/id/{id}")
    SupplierDTO getSupplierById(@PathVariable int id);
}
