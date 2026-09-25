package com.payment.gateway.exception;

import java.util.UUID;

public class PaymentIntentNotFoundException extends RuntimeException {
    public PaymentIntentNotFoundException(UUID id) {
        super("Payment Intent not found: " + id);
    }
}
