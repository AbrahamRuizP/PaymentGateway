package com.payment.gateway.exception;

import java.util.UUID;

public class PaymentMethodNotFoundException extends RuntimeException {
    public PaymentMethodNotFoundException(UUID id) {
        super("Payment method with id " + id + " not found");
    }
}
