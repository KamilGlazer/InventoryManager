package org.example.orderservice.config.exceptions;

public class ProductIsNotAvaliableException extends RuntimeException {
    public ProductIsNotAvaliableException() {
        super("Product is not avaliable!");
    }
}
