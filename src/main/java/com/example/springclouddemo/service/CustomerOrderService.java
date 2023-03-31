package com.example.springclouddemo.service;

import com.example.springclouddemo.component.Mapper;
import com.example.springclouddemo.constants.ResponseHeader;
import com.example.springclouddemo.entity.CustomerEntity;
import com.example.springclouddemo.entity.OrderEntity;
import com.example.springclouddemo.enums.OrderStatus;
import com.example.springclouddemo.dto.OrderDto;
import com.example.springclouddemo.exceptions.*;
import com.example.springclouddemo.repository.CustomerRepository;
import com.example.springclouddemo.repository.OrderRepository;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.springclouddemo.constants.EntityName.*;

@Service
@AllArgsConstructor
public class CustomerOrderService {

    @Value("${spring.application.name}")
    private static String applicationName;

    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final Mapper mapper;

    public ResponseEntity<OrderDto> postNewOrder(String customerId, OrderEntity order) {
        CustomerEntity foundedCustomer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        foundedCustomer.getOrders().add(order);
        customerRepository.save(foundedCustomer);

        String message = "A new %s is created with identifier %s".formatted(ORDER, order.getId());

        return ResponseEntity.created(URI.create("/api/v1/customerOrders/%s".formatted(customerId)))
                .header(ResponseHeader.MESSAGE.formatted(applicationName), message)
                .header(ResponseHeader.PARAMS.formatted(applicationName), customerId)
                .body(mapper.mapOrderToDto(order));
    }

    public ResponseEntity<OrderDto> updateOrder(String customerId, OrderDto order) {
        CustomerEntity foundedCustomer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
        OrderEntity mappedOrder = mapper.mapDtoToOrder(order);
        OrderEntity oldOrder = orderRepository.findByCustomerIdAndProductsAndShippingAddress(
                    customerId, mappedOrder.getProducts(), mappedOrder.getShippingAddress()
                ).orElseThrow(OrderNotFoundException::new);
        String message = "The %s with identifier %s was updated".formatted(ORDER, oldOrder.getId());

        orderRepository.delete(oldOrder);
        foundedCustomer.getOrders().add(oldOrder.copyOf(mapper.mapDtoToOrder(order)));
        customerRepository.save(foundedCustomer);

        return ResponseEntity.ok()
                .header(ResponseHeader.MESSAGE.formatted(applicationName), message)
                .header(ResponseHeader.PARAMS.formatted(applicationName), customerId)
                .body(mapper.mapOrderToDto(oldOrder));
    }

    public ResponseEntity<Set<OrderDto>> getAllCustomerOrdersByIdAndStatus(
            String customerId,
            @Nullable OrderStatus status) {
        if (customerRepository.findById(customerId).isEmpty()) throw new CustomerNotFoundException();

        List<OrderEntity> foundedOrders = Objects.isNull(status)
                ? orderRepository.findAllByCustomerId(customerId)
                : orderRepository.findAllByCustomerIdAndStatus(customerId, status);
        String message = "All %s entities from the %s with identifier %s was founded".formatted(
                ORDER, CUSTOMER, customerId
        );

        return ResponseEntity.ok()
                .header(ResponseHeader.MESSAGE.formatted(applicationName), message)
                .header(ResponseHeader.PARAMS.formatted(applicationName), customerId)
                .header(ResponseHeader.SIZE.formatted(applicationName), String.valueOf(foundedOrders.size()))
                .body(
                        foundedOrders.stream()
                                .map(mapper::mapOrderToDto)
                                .collect(Collectors.toSet())
                );
    }

    public ResponseEntity<OrderDto> getOrderById(String customerId, String orderId) {
        if (customerRepository.findById(customerId).isEmpty()) throw new CustomerNotFoundException();

        OrderEntity foundedOrder = orderRepository.findByIdAndCustomerId(orderId, customerId)
                .orElseThrow(OrderNotFoundException::new);
        String message = "%s with identifier %s from the %s with identifier %s was founded".formatted(
                ORDER, orderId, CUSTOMER, customerId
        );

        return ResponseEntity.ok()
                .header(ResponseHeader.MESSAGE.formatted(applicationName), message)
                .header(ResponseHeader.PARAMS.formatted(applicationName), customerId, orderId)
                .body(mapper.mapOrderToDto(foundedOrder));
    }

    public ResponseEntity<Void> deleteOrder(String customerId, String orderId) {
        if (customerRepository.findById(customerId).isEmpty()) throw new CustomerNotFoundException();

        OrderEntity foundedOrder = orderRepository.findByIdAndCustomerId(orderId, customerId)
                .orElseThrow(OrderNotFoundException::new);
        String message = "%s with identifier %s from the %s with identifier %s was deleted".formatted(
                ORDER, orderId, CUSTOMER, customerId
        );

        orderRepository.delete(foundedOrder);

        return ResponseEntity.noContent()
                .header(ResponseHeader.MESSAGE.formatted(applicationName), message)
                .header(ResponseHeader.PARAMS.formatted(applicationName), customerId, orderId)
                .build();
    }

}
