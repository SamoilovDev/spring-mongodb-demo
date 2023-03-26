package com.example.springclouddeno.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Document(collation = "order")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotBlank(message = "Customer id is required!")
    private String customerId;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @Version
    private Integer version;

    private OrderStatus status = OrderStatus.CREATED;

    private Boolean paymentStatus = Boolean.FALSE;

    @NotNull(message = "Payment method is required!")
    private PaymentType paymentMethod;

    @NotBlank(message = "Payment details are required!")
    private String paymentDetails;

    @Valid
    private Address shippingAddress;

    @NotEmpty
    private Set<@Valid Product> products;

}
