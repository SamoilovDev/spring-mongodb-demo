package com.example.springclouddemo.service;

import com.example.springclouddemo.constants.ResponseHeader;
import com.example.springclouddemo.domain.Customer;
import com.example.springclouddemo.domain.Order;
import com.example.springclouddemo.exceptions.*;
import com.example.springclouddemo.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.example.springclouddemo.constants.EntityName.CUSTOMER;
import static com.example.springclouddemo.constants.EntityName.ORDER;

@Service
@AllArgsConstructor
public class CustomerOrderService {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private final CustomerRepository customerRepository;


    public ResponseEntity<Order> postNewOrder(String customerId, Order order) {
        Customer foundedCustomer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        foundedCustomer.addOrder(order);
        customerRepository.save(foundedCustomer);

        String message = "A new %s is created with identifier %s".formatted(ORDER, order.getId());

        return ResponseEntity.ok()
                .header(ResponseHeader.MESSAGE.formatted(applicationName), message)
                .header(ResponseHeader.PARAMS.formatted(applicationName), customerId)
                .body(order);
    }

    public ResponseEntity<Order> updateOrder(String customerId, Order order) {
        Customer foundedCustomer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
        Order oldOrder = foundedCustomer.getOrders()
                .stream()
                .filter(_order -> _order.getId().equals(order.getId()))
                .findFirst()
                .orElseThrow(OrderNotFoundException::new);
        String message = "The %s with identifier %s was updated".formatted(ORDER, order.getId());

        foundedCustomer.getOrders().remove(oldOrder); // todo change
        foundedCustomer.getOrders().add(order);
        customerRepository.save(foundedCustomer);

        return ResponseEntity.ok()
                .header(ResponseHeader.MESSAGE.formatted(applicationName), message)
                .header(ResponseHeader.PARAMS.formatted(applicationName), customerId)
                .body(order);
    }

    public ResponseEntity<Set<Order>> getAllOrders(String customerId) {
        Set<Order> allOrders = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new)
                .getOrders();
        String message = "All %s entities from the %s with identifier %s was founded".formatted(
                ORDER, CUSTOMER, customerId
        );

        return ResponseEntity.ok()
                .header(ResponseHeader.MESSAGE.formatted(applicationName), message)
                .header(ResponseHeader.PARAMS.formatted(applicationName), customerId)
                .body(allOrders);
    }

    public ResponseEntity<Order> getOrderById(String customerId, String orderId) {
        Order foundedOrder = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new)
                .getOrders()
                .stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .orElseThrow(OrderNotFoundException::new);
        String message = "%s with identifier %s from the %s with identifier %s was founded".formatted(
                ORDER, orderId, CUSTOMER, customerId
        );

        return ResponseEntity.ok()
                .header(ResponseHeader.MESSAGE.formatted(applicationName), message)
                .header(ResponseHeader.PARAMS.formatted(applicationName), customerId, orderId)
                .body(foundedOrder);
    }

    public ResponseEntity<Void> deleteOrder(String customerId, String orderId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
        Order foundedOrder = customer.getOrders()
                .stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .orElseThrow(OrderNotFoundException::new);
        String message = "%s with identifier %s from the %s with identifier %s was deleted".formatted(
                ORDER, orderId, CUSTOMER, customerId
        );

        customer.getOrders().remove(foundedOrder);
        customerRepository.save(customer);

        return ResponseEntity.noContent()
                .header(ResponseHeader.MESSAGE.formatted(applicationName), message)
                .header(ResponseHeader.PARAMS.formatted(applicationName), customerId, orderId)
                .build();
    }

}
