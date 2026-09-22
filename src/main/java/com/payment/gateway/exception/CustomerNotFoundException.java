package com.payment.gateway.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(java.util.UUID id) {
        super("Customer not found: " + id);
    }
}
