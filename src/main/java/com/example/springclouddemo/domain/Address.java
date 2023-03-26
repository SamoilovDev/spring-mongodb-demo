package com.example.springclouddemo.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull(message = "Street name is required!")
    private String streetName;

    @NotNull(message = "Street number is required!")
    private String streetNumber;

    private String additionalInfo;

    @NotNull(message = "Zip code is required!")
    private String zipCode;

    @NotNull(message = "City is required!")
    private String city;

    private String state;

    @NotNull(message = "Country is required!")
    private String country;

}
