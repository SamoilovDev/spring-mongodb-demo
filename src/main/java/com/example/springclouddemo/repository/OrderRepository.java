package com.example.springclouddemo.repository;

import com.example.springclouddemo.entity.AddressEntity;
import com.example.springclouddemo.entity.OrderEntity;
import com.example.springclouddemo.enums.OrderStatus;
import com.example.springclouddemo.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface OrderRepository extends MongoRepository<OrderEntity, String> {

    @Query("{ 'customerId' :  ?0 }")
    List<OrderEntity> findAllByCustomerId(@Param("customerId") String customerId);

    @Query("{ 'customerId' : ?0, 'status' : ?1 }")
    List<OrderEntity> findAllByCustomerIdAndStatus(@Param("customerId") String customerId, @Param("status")OrderStatus status);

    @Query("{ _id : ?0, customerId : ?1 }")
    Optional<OrderEntity> findByIdAndCustomerId(@Param("id") String id, @Param("customerId") String customerId);

    Optional<OrderEntity> findByCustomerIdAndProductsAndShippingAddress(
            String customerId, Set<ProductEntity> products, AddressEntity shippingAddress
    );

}
