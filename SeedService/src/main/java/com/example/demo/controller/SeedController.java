package com.example.demo.controller;

import com.example.demo.dto.OrderDTO;
import com.example.demo.service.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class SeedController {

    @Autowired
    private SeedService seedService;

    @GetMapping("/")
    public Map<String, String> seed() {
         seedService.seedAll();
    }

}
