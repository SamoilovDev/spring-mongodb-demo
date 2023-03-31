package com.example.springclouddemo.dto;

import com.example.springclouddemo.enums.OrderStatus;
import com.example.springclouddemo.enums.PaymentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    @NotBlank(message = "Customer id is required!")
    private String customerId;

    private OrderStatus status;

    private Boolean paymentStatus;

    @NotNull(message = "Payment method is required!")
    private PaymentType paymentMethod;

    private String paymentDetails;

    @Valid
    private AddressDto shippingAddress;

    @NotEmpty
    private Set<@Valid ProductDto> products;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductDto {

        @NotBlank(message = "Name is required!")
        private String name;

        private String description;

        @JsonProperty(value = "model_number")
        private String modelNumber;

        @JsonProperty(value = "manufacturer_name")
        @NotBlank(message = "Manufacture name is required!")
        private String manufacturerName;

        @NotNull(message = "Price is required!")
        @Min(value = 0, message = "Price cannot be less than zero")
        private Double price;

        @JsonProperty(value = "detail_info")
        private String detailInfo;

        @JsonProperty(value = "image_url")
        private String imageUrl;

        @NotNull(message = "Quantity is required!")
        @Min(value = 0, message = "Quantity cannot be less than zero")
        private Integer quantity;

    }


}

