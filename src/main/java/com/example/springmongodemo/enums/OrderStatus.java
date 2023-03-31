package com.example.springmongodemo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    CREATED("CREATED"),

    DELIVERING("DELIVERING"),

    DELIVERED("DELIVERED"),

    CANCELLED("CANCELLED"),

    DELETED("DELETED"),

    RETURNING("RETURNING"),

    RETURNED("RETURNED");

    private final String value;

}
