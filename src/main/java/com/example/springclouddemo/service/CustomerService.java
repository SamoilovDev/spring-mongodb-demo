package com.example.springclouddemo.service;

import com.example.springclouddemo.component.Mapper;
import com.example.springclouddemo.constants.ResponseHeader;
import com.example.springclouddemo.entity.CustomerEntity;
import com.example.springclouddemo.dto.CustomerDto;
import com.example.springclouddemo.exceptions.CustomerAlreadyExistsException;
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

    @Autowired
    private final Mapper mapper;

    public ResponseEntity<CustomerDto> saveCustomer(CustomerDto customer) {
        if (customerRepository.findByFirstNameAndLastName(customer.getFirstName(), customer.getLastName()).isPresent()) {
            throw new CustomerAlreadyExistsException();
        }
        CustomerEntity savedCustomer = customerRepository.save(mapper.mapDtoToCustomer(customer));
        String message = "A new %s is created with identifier %s".formatted(CUSTOMER, savedCustomer.getId());

        return ResponseEntity.created(URI.create("/api/v1/customers"))
                .header(ResponseHeader.MESSAGE.formatted(applicationName), message)
                .header(ResponseHeader.PARAMS.formatted(applicationName), savedCustomer.getId())
                .body(mapper.mapCustomerToDto(savedCustomer));
    }

    public ResponseEntity<CustomerDto> updateCustomer(CustomerDto customer) {
        CustomerEntity oldCustomer = customerRepository.findByFirstNameAndLastName(customer.getFirstName(), customer.getLastName())
                .orElseThrow(CustomerNotFoundException::new);
        CustomerEntity updatedCustomer = customerRepository.save(oldCustomer.copyOf(customer, mapper));
        String message = "The %s with identifier %s was updated".formatted(CUSTOMER, updatedCustomer.getId());

        return ResponseEntity.ok()
                .header(ResponseHeader.MESSAGE.formatted(applicationName), message)
                .header(ResponseHeader.PARAMS.formatted(applicationName), updatedCustomer.getId())
                .body(mapper.mapCustomerToDto(updatedCustomer));
    }

    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerEntity> allCustomers = customerRepository.findAll();
        String message = "All %s entities was founded".formatted(CUSTOMER);

        return ResponseEntity.ok()
                .header(ResponseHeader.MESSAGE.formatted(applicationName), message)
                .header(ResponseHeader.SIZE.formatted(applicationName), String.valueOf(allCustomers.size()))
                .body(allCustomers.stream().map(mapper::mapCustomerToDto).toList());
    }

    public ResponseEntity<CustomerDto> getCustomerById(String id) {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
        String message = "The %s with identifier %s was founded".formatted(CUSTOMER, id);

        return ResponseEntity.ok()
                .header(ResponseHeader.MESSAGE.formatted(applicationName), message)
                .header(ResponseHeader.PARAMS.formatted(applicationName), id)
                .body(mapper.mapCustomerToDto(customer));
    }

    public ResponseEntity<Void> deleteCustomerById(String id) {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
        String message = "The %s with identifier %s was deleted".formatted(CUSTOMER, id);

        customerRepository.delete(customer);

        return ResponseEntity.noContent()
                .header(ResponseHeader.MESSAGE.formatted(applicationName), message)
                .header(ResponseHeader.PARAMS.formatted(applicationName), id)
                .build();
    }

}
