package com.example.springclouddemo.controller;

import com.example.springclouddemo.domain.Health;
import com.example.springclouddemo.domain.HealthStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class HealthController {

    @GetMapping(value = "/health", produces = "application/json")
    public ResponseEntity<Health> getHealth() {
        log.debug("REST request to get the Health Status");
        return ResponseEntity.ok(
                Health.builder()
                        .status(HealthStatus.UP)
                        .build()
        );
    }

}
