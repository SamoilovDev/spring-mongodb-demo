package com.example.springclouddemo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HealthStatus {

    UP("UP"),

    DOWN("DOWN");

    private final String status;

}
