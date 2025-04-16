package com.example.demo.services;

import com.example.demo.clients.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {
    @Autowired
    private OrderClient orderClient;
    @Autowired
    ProductClient productClient;
    @Autowired
    SupplierClient supplierClient;
    @Autowired
    SaleClient saleClient;
    @Autowired
    UserClient userClient;

    public 
}
