package com.example.springmongodemo.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Customer doesn't exist!")
public class CustomerNotFoundException extends RuntimeException {
}
