package com.example.springclouddemo.entity;

import com.example.springclouddemo.enums.OrderStatus;
import com.example.springclouddemo.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Document(collation = "order")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String customerId;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @Version
    private Integer version;

    private OrderStatus status = OrderStatus.CREATED;

    private Boolean paymentStatus = Boolean.FALSE;

    private PaymentType paymentMethod;

    private String paymentDetails;

    private AddressEntity shippingAddress;

    private Set<ProductEntity> products;

    public OrderEntity copyOf(OrderEntity that) {
        this.version += 1;
        this.updatedAt = Instant.now();
        this.status = that.getStatus();
        this.paymentStatus = that.getPaymentStatus();
        this.paymentMethod = that.getPaymentMethod();
        this.paymentDetails = that.getPaymentDetails();
        this.shippingAddress = that.getShippingAddress();
        this.products = that.getProducts();

        return this;
    }

}
