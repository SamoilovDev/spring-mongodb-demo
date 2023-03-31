package com.example.springmongodemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("street_name")
    private String streetName;

    @Field("street_number")
    private String streetNumber;

    @Field("additional_information")
    private String additionalInfo;

    @Field("zip_code")
    private String zipCode;

    @Field("city")
    private String city;

    @Field("state")
    private String state;

    @Field("country")
    private String country;

}
