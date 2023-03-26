package com.example.springclouddeno.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class CustomerOrderResource {

    private static final String ENTITY_NAME = "order";

    @Value("${spring.application.name}")
    private String applicationName;

}
