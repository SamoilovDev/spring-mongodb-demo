package com.example.springclouddemo.controller;

import com.example.springclouddemo.dto.CustomerDto;
import com.example.springclouddemo.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CustomerController {

    @Autowired
    private final CustomerService customerService;

    @Operation(summary = "Posting new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer was posted successfully!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Customer at the body is not valid!",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Customer was already posted!",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content) })
    @PostMapping("/customers")
    public ResponseEntity<CustomerDto> postCustomer(@Valid @RequestBody CustomerDto customer) {
        log.debug("REST request to save Customer : {}", customer);
        return customerService.saveCustomer(customer);
    }

    @Operation(summary = "Updating customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer was updated successfully!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Customer at the body is not valid!",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content) })
    @PutMapping("/customers")
    public ResponseEntity<CustomerDto> updateCustomer(@Valid @RequestBody CustomerDto customer) {
        log.debug("REST request to update Customer : {}", customer);
        return customerService.updateCustomer(customer);
    }

    @Operation(summary = "Getting all customers in the DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers was returned successfully!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content) })
    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        log.debug("REST request to get all Customers");
        return customerService.getAllCustomers();
    }

    @Operation(summary = "Searching customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer was returned successfully!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDto.class)) }),
            @ApiResponse(responseCode = "400", description = "ID is blank!",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer wasn't founded!",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content) })
    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@NotBlank @PathVariable String id) {
        log.debug("REST request to get Customer : {}", id);
        return customerService.getCustomerById(id);
    }

    @Operation(summary = "Deleting customer by id at the path")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer was deleted successfully!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema) }),
            @ApiResponse(responseCode = "400", description = "ID at the path is blank!",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer wasn't founded!",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content) })
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@NotBlank @PathVariable String id) {
        log.debug("REST request to delete Customer : {}", id);
        return customerService.deleteCustomerById(id);
    }

}
