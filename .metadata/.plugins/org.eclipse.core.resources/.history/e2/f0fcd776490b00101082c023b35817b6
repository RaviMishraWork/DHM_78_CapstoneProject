package com.example.demo.service;

import com.example.demo.data.Alert;
import com.example.demo.dto.AlertDTO;
import com.example.demo.repository.AlertRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    @Transactional
    public List<AlertDTO> getAlerts() {
        List<Alert> alertList = alertRepository.findAll();

        return alertList.stream().map(alert ->
                AlertDTO.builder()
                        .productId(alert.getProductId())
                        .quantity(alert.getProductQuantity())
                        .build()).collect(Collectors.toList());
    }

    @Transactional
    public Alert createAlert(AlertDTO alertDTO) {

        Alert alert = Alert.builder()
                .productId(alertDTO.getProductId())
                .productQuantity(alertDTO.getQuantity())
                .build();
        return alertRepository.save(alert);
    }
}
