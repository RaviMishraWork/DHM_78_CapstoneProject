package com.example.demo.clients;

import com.example.demo.dto.SaleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(
        name = "SaleManager",
        url = "http://localhost:8099",
        path = "/sales"
)
public interface SaleClient {
    @GetMapping("/pendingdemands")
    public Map<Integer,Integer> getPendingDemands();
    @GetMapping()
    public List<SaleDTO> getAllSales();
    @GetMapping("/user/{username}")
    public List<SaleDTO> getSalesByUsername(@PathVariable String username);
    @GetMapping("/sku/{sku}")
    public List<SaleDTO> getSalesBySku(@PathVariable Integer sku);
}
