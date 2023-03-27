package com.example.springclouddemo.controller;

import com.example.springclouddemo.domain.Order;
import com.example.springclouddemo.service.CustomerOrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CustomerOrderController {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "order";

    @Autowired
    private final CustomerOrderService customerOrderService;

    @PostMapping("/customerOrder/{customerId}")
    @Transactional
    public ResponseEntity<Map<String, String>> postNewOrder(
            @PathVariable String customerId,
            @Valid @RequestBody Order order
    ) {
        if (!Objects.equals(null, order.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A new order cannot already have an ID");
        }
        ResponseEntity<Map<String, String>> response = customerOrderService.postNewOrder(customerId, order);
        String orderId = Objects.requireNonNull(response.getBody()).get("order_id");
        String message = "A new %s is created with identifier %s".formatted(ENTITY_NAME, orderId);

        response.getHeaders().add("X-".concat(applicationName).concat("-alert"), message);
        response.getHeaders().add("X-".concat(applicationName).concat("-params"), orderId);

        return response;
    }
}

