package com.example.springmongodemo.controller;

import com.example.springmongodemo.enums.OrderStatus;
import com.example.springmongodemo.dto.OrderDto;
import com.example.springmongodemo.service.CustomerOrderService;
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

import java.util.Set;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CustomerOrderController {

    @Autowired
    private final CustomerOrderService customerOrderService;

    @Operation(summary = "Posting new order to the customer with identifier at the path")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order was posted successfully!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDto.class)) }),
            @ApiResponse(responseCode = "400", description = "CustomerId at the path is blank!",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found!",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Order was already posted!",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content) })
    @PostMapping("/customerOrders/{customerId}")
    public ResponseEntity<OrderDto> postNewOrder(
            @NotBlank @PathVariable String customerId,
            @Valid @RequestBody OrderDto order
    ) {
        log.debug("REST request to save Order : {} for Customer ID: {}", order, customerId);
        return customerOrderService.postNewOrder(customerId, order);
    }

    @Operation(summary = "Updating order from the customer with identifier at the path")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order was updated successfully!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDto.class)) }),
            @ApiResponse(responseCode = "400", description = "CustomerId at the path is blank!",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer or order not found!",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content) })
    @PutMapping("/customerOrders/{customerId}")
    public ResponseEntity<OrderDto> updateOrder(
            @NotBlank @PathVariable String customerId,
            @Valid @RequestBody OrderDto order
    ) {
        log.debug("REST request to update Order : {} for Customer ID: {}", order, customerId);
        return customerOrderService.updateOrder(customerId, order);
    }

    @Operation(summary = "Getting all orders from the customer with identifier at the path")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders were returned successfully!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDto.class)) }),
            @ApiResponse(responseCode = "400", description = "CustomerId at the path is blank!",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found!",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content) })
    @GetMapping("/customerOrders/{customerId}")
    public ResponseEntity<Set<OrderDto>> getAllOrders(
            @NotBlank @PathVariable String customerId,
            @RequestParam(required = false) String status) {
        log.debug("REST request to get all Orders for Customer: {}", customerId);
        return customerOrderService.getAllCustomerOrdersByIdAndStatus(customerId, OrderStatus.valueOf(status));
    }

    @Operation(summary = "Getting order with identifier at the path from the customer with identifier at the path")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order was returned successfully!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDto.class)) }),
            @ApiResponse(responseCode = "400", description = "CustomerId or orderId at the path is blank!",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer or order not found!",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content) })
    @GetMapping("/customerOrders/{customerId}/{orderId}")
    public ResponseEntity<OrderDto> getOrder(
            @NotBlank @PathVariable String customerId,
            @NotBlank @PathVariable String orderId
    ) {
        log.debug("REST request to get Order : {} for Customer: {}", orderId, customerId);
        return customerOrderService.getOrderById(customerId, orderId);
    }

    @Operation(summary = "Delete order with identifier at the path from customer with identifier at the path")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order was deleted successfully!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema) }),
            @ApiResponse(responseCode = "400", description = "CustomerId or orderId at the path is blank!",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer or order not found!",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content) })
    @DeleteMapping("/customerOrders/{customerId}/{orderId}")
    public ResponseEntity<Void> deleteOrder(
            @NotBlank @PathVariable String customerId,
            @NotBlank @PathVariable String orderId
    ) {
        log.debug("REST request to delete Order : {} for Customer: {}", orderId, customerId);
        return customerOrderService.deleteOrder(customerId, orderId);
    }

}

