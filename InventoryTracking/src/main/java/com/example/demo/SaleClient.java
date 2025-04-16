package com.example.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(
        name = "SaleManager",
        url = "http://localhost:8099",
        path = "/sales"
)
public interface SaleClient {
    @GetMapping("/pendingdemands")
    public Map<Integer,Integer> getPendingDemands();
}
