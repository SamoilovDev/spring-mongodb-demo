package com.example.springmongodemo.entity;

import jakarta.validation.constraints.NotNull;
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
public class ProductEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull(message = "Id is required!")
    private String id;

    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @Field("model_number")
    private String modelNumber;

    @Field("manufacture_name")
    private String manufacturerName;

    @Field("price")
    private Double price;

    @Field("detail_info")
    private String detailInfo;

    @Field("image_url")
    private String imageUrl;

    @Field("quantity")
    private Integer quantity;

}
