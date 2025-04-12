package com.example.demo.controller;

import com.example.demo.dto.SaleDTO;
import com.example.demo.dto.SaleRequestDTO;
import com.example.demo.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SaleService saleService;

    @PostMapping("/place")
    public SaleDTO createSale(@RequestBody SaleDTO saleRequest) {
        return saleService.createSale(saleRequest);
    }

    @GetMapping()
    public List<SaleDTO> getSales() {
        return saleService.getAllSale();
    }

    @GetMapping("/{saleId}")
    public SaleDTO getSale(@PathVariable UUID saleId) {
        return saleService.getSaleById(saleId);
    }

    @PutMapping("/{saleId}")
    public SaleDTO updateSale(@PathVariable UUID saleId, @RequestBody SaleDTO saleRequest) {
        return saleService.updateSale(saleId, saleRequest);
    }

    @DeleteMapping("{saleId}")
    public SaleDTO deleteSale(@PathVariable UUID saleId) {
        return saleService.deleteSale(saleId);
    }
    @GetMapping("/user/{username}")
    public List<SaleDTO> getSalesByUsername(@PathVariable String username) {
    	return saleService.getSalesByUsername(username);
    }
    @PostMapping("mark-complete/{saleId}")
    public SaleDTO markSaleAsCompleted(@PathVariable UUID saleId) {
        return saleService.markSaleAsCompleted(saleId);
    }
}
