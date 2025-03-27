package com.example.demo.controller;

import com.example.demo.dto.AlertDTO;
import com.example.demo.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alerts")
public class AlertsController {

    @Autowired
    AlertService alertService;

    @GetMapping
    public List<AlertDTO> getAlerts() {
        return alertService.getAlerts();
    }
}
