package com.example.springclouddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class SpringMongoDBMicroservicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMongoDBMicroservicesApplication.class, args);
    }

}
