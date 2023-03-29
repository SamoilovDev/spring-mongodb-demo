package com.example.springclouddemo.domain;

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
import java.util.HashSet;
import java.util.Set;

@Document(collection = "customer")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("first_name")
    private String firstName;

    @Field("middle_name")
    private String middleName;

    @NotNull
    @Field("last_name")
    private String lastName;

    @Field("payment_details")
    private String paymentDetails;

    @Field("created_at")
    @CreatedDate
    private Instant createdAt;

    @Field("updated_at")
    @LastModifiedDate
    private Instant updatedAt;

    @Version
    public Integer version;

    @Field("orders")
    private Set<Order> orders = new HashSet<>();

    @Field("billing_address")
    private Address billingAddress;

    public Customer copyOf(Customer that) {
        this.firstName = that.getFirstName();
        this.middleName = that.getMiddleName();
        this.lastName = that.getLastName();
        this.paymentDetails = that.getPaymentDetails();
        this.updatedAt = Instant.now();
        this.version += 1;
        this.orders = that.getOrders();
        this.billingAddress = that.getBillingAddress();

        return this;
    }

}
