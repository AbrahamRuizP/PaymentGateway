package com.payment.gateway.exception;

import com.payment.gateway.entity.enums.PaymentIntentStatus;

import java.util.UUID;

public class InvalidPaymentIntentTransitionException extends RuntimeException {
    public InvalidPaymentIntentTransitionException(
            PaymentIntentStatus oldStatus,
            PaymentIntentStatus newStatus,
            UUID id
    ) {
        super("Invalid status transition for payment intent: " + id
                + ". From " + oldStatus + " to " + newStatus);
    }
}
