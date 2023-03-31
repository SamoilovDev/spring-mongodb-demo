package com.example.springclouddemo.repository;

import com.example.springclouddemo.entity.CustomerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<CustomerEntity, String> {

    @Query("{ first_name : ?0, last_name : ?1 }")
    Optional<CustomerEntity> findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
