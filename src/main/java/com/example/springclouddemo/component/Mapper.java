package com.example.springclouddemo.component;

import com.example.springclouddemo.entity.AddressEntity;
import com.example.springclouddemo.entity.CustomerEntity;
import com.example.springclouddemo.entity.OrderEntity;
import com.example.springclouddemo.entity.ProductEntity;
import com.example.springclouddemo.dto.AddressDto;
import com.example.springclouddemo.dto.CustomerDto;
import com.example.springclouddemo.dto.OrderDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class Mapper {

    public CustomerDto mapCustomerToDto(CustomerEntity customer) {
        return CustomerDto.builder()
                .firstName(customer.getFirstName())
                .middleName(customer.getMiddleName())
                .lastName(customer.getLastName())
                .paymentDetails(customer.getPaymentDetails())
                .billingAddress(this.mapDtoToAddress(customer.getBillingAddress()))
                .orders(
                        customer.getOrders()
                                .stream()
                                .map(this::mapOrderToDto)
                                .collect(Collectors.toSet())
                )
                .build();
    }

    public CustomerEntity mapDtoToCustomer(CustomerDto customerDto) {
        return CustomerEntity.builder()
                .firstName(customerDto.getFirstName())
                .middleName(customerDto.getMiddleName())
                .lastName(customerDto.getLastName())
                .paymentDetails(customerDto.getPaymentDetails())
                .billingAddress(this.mapDtoToAddress(customerDto.getBillingAddress()))
                .orders(
                        customerDto.getOrders()
                                .stream()
                                .map(this::mapDtoToOrder)
                                .collect(Collectors.toSet())
                )
                .build();
    }



    public OrderDto mapOrderToDto(OrderEntity order) {
        return OrderDto.builder()
                .customerId(order.getCustomerId())
                .status(order.getStatus())
                .paymentMethod(order.getPaymentMethod())
                .paymentStatus(order.getPaymentStatus())
                .paymentDetails(order.getPaymentDetails())
                .shippingAddress(this.mapDtoToAddress(order.getShippingAddress()))
                .products(
                        order.getProducts()
                                .stream()
                                .map(this::mapProductToDto)
                                .collect(Collectors.toSet())
                )
                .build();
    }

    public OrderEntity mapDtoToOrder(OrderDto orderDto) {
        return OrderEntity.builder()
                .customerId(orderDto.getCustomerId())
                .status(orderDto.getStatus())
                .paymentMethod(orderDto.getPaymentMethod())
                .paymentStatus(orderDto.getPaymentStatus())
                .paymentDetails(orderDto.getPaymentDetails())
                .shippingAddress(this.mapDtoToAddress(orderDto.getShippingAddress()))
                .products(
                        orderDto.getProducts()
                                .stream()
                                .map(this::mapDtoToProduct)
                                .collect(Collectors.toSet())
                )
                .build();
    }



    public AddressDto mapDtoToAddress(AddressEntity address) {
        return AddressDto.builder()
                .country(address.getCountry())
                .state(address.getState())
                .city(address.getCity())
                .streetName(address.getStreetName())
                .streetNumber(address.getStreetNumber())
                .zipCode(address.getZipCode())
                .additionalInfo(address.getAdditionalInfo())
                .build();
    }

    public AddressEntity mapDtoToAddress(AddressDto addressDto) {
        return AddressEntity.builder()
                .country(addressDto.getCountry())
                .state(addressDto.getState())
                .city(addressDto.getCity())
                .streetName(addressDto.getStreetName())
                .streetNumber(addressDto.getStreetNumber())
                .zipCode(addressDto.getZipCode())
                .additionalInfo(addressDto.getAdditionalInfo())
                .build();
    }



    private OrderDto.ProductDto mapProductToDto(ProductEntity product) {
        return OrderDto.ProductDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .detailInfo(product.getDetailInfo())
                .manufacturerName(product.getManufacturerName())
                .modelNumber(product.getModelNumber())
                .quantity(product.getQuantity())
                .imageUrl(product.getImageUrl())
                .build();
    }

    private ProductEntity mapDtoToProduct(OrderDto.ProductDto productDto) {
        return ProductEntity.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .detailInfo(productDto.getDetailInfo())
                .manufacturerName(productDto.getManufacturerName())
                .modelNumber(productDto.getModelNumber())
                .quantity(productDto.getQuantity())
                .imageUrl(productDto.getImageUrl())
                .build();
    }

}
