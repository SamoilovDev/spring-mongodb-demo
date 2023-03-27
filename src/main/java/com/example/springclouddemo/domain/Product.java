package com.example.springclouddemo.domain;

import jakarta.validation.constraints.Min;
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
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull(message = "Id is required!")
    private String id;

    @NotNull(message = "Name is required!")
    private String name;

    private String description;

    private String modelNumber;

    @NotNull
    private String manufacturerName;

    @NotNull(message = "Price is required!")
    @Min(value = 0, message = "Price cannot be less than zero")
    private Double price;

    private String detailInfo;

    private String imageUrl;

    @NotNull(message = "Quantity is required!")
    @Min(value = 0, message = "Quantity cannot be less than zero")
    private Integer quantity;

}
