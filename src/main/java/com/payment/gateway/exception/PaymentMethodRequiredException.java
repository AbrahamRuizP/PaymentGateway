package com.payment.gateway.exception;

import java.util.UUID;

public class PaymentMethodRequiredException extends RuntimeException {
    public PaymentMethodRequiredException(UUID id) {
        super("Payment method is required for payment intent " + id);
    }
}
