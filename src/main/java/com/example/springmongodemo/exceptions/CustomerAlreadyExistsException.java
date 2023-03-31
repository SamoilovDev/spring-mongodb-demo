package com.example.springmongodemo.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(code = HttpStatus.CONFLICT, reason = "A new customer cannot already have an ID")
public class CustomerAlreadyExistsException extends RuntimeException {
}
