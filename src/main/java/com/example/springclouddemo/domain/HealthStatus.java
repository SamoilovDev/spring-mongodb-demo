package com.example.springclouddemo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum HealthStatus {

    UP("UP", HttpStatus.NO_CONTENT),

    DOWN("DOWN", HttpStatus.BAD_GATEWAY);

    private final String status;

    private final HttpStatus httpStatus;

}
