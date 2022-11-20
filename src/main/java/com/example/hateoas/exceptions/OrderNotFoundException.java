package com.example.hateoas.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(final String msg) {
        super(msg);
    }
}
