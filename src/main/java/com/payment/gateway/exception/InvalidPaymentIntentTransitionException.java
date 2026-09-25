package com.payment.gateway.exception;

import java.util.UUID;

public class InvalidPaymentIntentTransitionException extends RuntimeException {
    public InvalidPaymentIntentTransitionException(UUID id) {
        super("Invalid status transition for payment intent: " + id);
    }
}
