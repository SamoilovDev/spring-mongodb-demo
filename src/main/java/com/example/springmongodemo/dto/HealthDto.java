package com.example.springmongodemo.dto;

import com.example.springmongodemo.enums.HealthStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthDto {

    @NotNull(message = "Health status is required!")
    private HealthStatus status;

}
