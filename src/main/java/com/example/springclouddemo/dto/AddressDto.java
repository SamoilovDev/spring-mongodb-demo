package com.example.springclouddemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder(value = {
        "country",
        "state",
        "city",
        "street_name",
        "street_number",
        "zip_code",
        "additional_information"
})
public class AddressDto {

    @JsonProperty(value = "street_name")
    @NotBlank(message = "Street name is required!")
    private String streetName;

    @JsonProperty(value = "street_number")
    private String streetNumber;

    @JsonProperty(value = "additional_information")
    private String additionalInfo;

    @JsonProperty(value = "zip_code")
    @NotBlank(message = "Zip code is required!")
    private String zipCode;

    @NotBlank(message = "City is required!")
    private String city;

    private String state;

    @NotBlank(message = "Country is required!")
    private String country;

}
