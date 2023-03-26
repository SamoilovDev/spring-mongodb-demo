package com.example.springclouddeno.controller;

import com.example.springclouddeno.service.CustomerOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CustomerOrderController {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private final CustomerOrderService customerOrderService;

    @PostMapping("/customerOrder/{customerId}")
    public ResponseEntity<Map<String, String>>
}

