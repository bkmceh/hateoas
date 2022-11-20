package com.example.hateoas.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(final String msg) {
        super(msg);
    }
}
