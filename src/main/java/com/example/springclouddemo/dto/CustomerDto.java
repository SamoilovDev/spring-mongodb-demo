package com.example.springclouddemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    @JsonProperty(value = "first_name")
    @NotBlank(message = "First name is required!")
    private String firstName;

    @JsonProperty(value = "middle_name")
    private String middleName;

    @JsonProperty(value = "last_name")
    @NotBlank(message = "Last name is required!")
    private String lastName;

    @JsonProperty(value = "payment_details")
    private String paymentDetails;

    private Set<@Valid OrderDto> orders = new HashSet<>();

    @Valid
    @JsonProperty(value = "billing_address")
    private AddressDto billingAddress;

}
