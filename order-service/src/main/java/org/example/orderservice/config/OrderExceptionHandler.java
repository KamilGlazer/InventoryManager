package org.example.orderservice.config;


import org.example.orderservice.config.exceptions.ProductIsNotAvaliableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderExceptionHandler {


    @ExceptionHandler(ProductIsNotAvaliableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String productIsNotAvaliableException(ProductIsNotAvaliableException ex) {
        return ex.getMessage();
    }
}
