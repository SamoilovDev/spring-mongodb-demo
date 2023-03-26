package com.example.springclouddemo.service;

import com.example.springclouddemo.domain.Customer;
import com.example.springclouddemo.domain.Order;
import com.example.springclouddemo.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@Service
@AllArgsConstructor
public class CustomerOrderService {

    @Autowired
    private final CustomerRepository customerRepository;

    public ResponseEntity<Map<String, String>> postNewOrder(String customerId, Order order) {
        Customer foundedCustomer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        foundedCustomer.addOrder(order);
        customerRepository.save(foundedCustomer);

        return ResponseEntity.ok(
                customerRepository.findById(customerId).map(customer -> Map.of("order_id"))
        );
    }
}

@NoArgsConstructor
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Customer doesn't exist!")
class CustomerNotFoundException extends RuntimeException {
}