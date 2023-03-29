package com.example.springclouddemo.service;

import com.example.springclouddemo.constants.ResponseHeader;
import com.example.springclouddemo.domain.Customer;
import com.example.springclouddemo.exceptions.CustomerNotFoundException;
import com.example.springclouddemo.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

import static com.example.springclouddemo.constants.EntityName.*;

@Service
@AllArgsConstructor
public class CustomerService {

    @Value("${spring.application.name}")
    private static String applicationName;

    @Autowired
    private final CustomerRepository customerRepository;

    public ResponseEntity<Customer> saveCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        String message = "A new %s is created with identifier %s".formatted(CUSTOMER, savedCustomer.getId());

        return ResponseEntity.created(URI.create("/api/v1/customers"))
                .header(ResponseHeader.MESSAGE.formatted(applicationName), message)
                .header(ResponseHeader.PARAMS.formatted(applicationName), savedCustomer.getId())
                .body(savedCustomer);
    }

    public ResponseEntity<Customer> updateCustomer(Customer customer) {
        Customer oldCustomer = customerRepository.findById(customer.getId())
                .orElseThrow(CustomerNotFoundException::new);
        Customer updatedCustomer = customerRepository.save(oldCustomer.copyOf(customer));
        String message = "The %s with identifier %s was updated".formatted(CUSTOMER, updatedCustomer.getId());

        return ResponseEntity.ok()
                .header(ResponseHeader.MESSAGE.formatted(applicationName), message)
                .header(ResponseHeader.PARAMS.formatted(applicationName), updatedCustomer.getId())
                .body(updatedCustomer);
    }

    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> allCustomers = customerRepository.findAll();
        String message = "All %s entities was founded".formatted(CUSTOMER);

        return ResponseEntity.ok()
                .header(ResponseHeader.MESSAGE.formatted(applicationName), message)
                .header(ResponseHeader.SIZE.formatted(applicationName), String.valueOf(allCustomers.size()))
                .body(allCustomers);
    }

    public ResponseEntity<Customer> getCustomerById(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
        String message = "The %s with identifier %s was founded".formatted(CUSTOMER, id);

        return ResponseEntity.ok()
                .header(ResponseHeader.MESSAGE.formatted(applicationName), message)
                .header(ResponseHeader.PARAMS.formatted(applicationName), id)
                .body(customer);
    }

    public ResponseEntity<Void> deleteCustomerById(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
        String message = "The %s with identifier %s was deleted".formatted(CUSTOMER, id);

        customerRepository.delete(customer);

        return ResponseEntity.noContent()
                .header(ResponseHeader.MESSAGE.formatted(applicationName), message)
                .header(ResponseHeader.PARAMS.formatted(applicationName), id)
                .build();
    }

}
