package com.payment.gateway.entity.enums;

public enum PaymentIntentStatus {
    REQUIRES_PAYMENT_METHOD,
    REQUIRES_CONFIRMATION,
    PROCESSING,
    SUCCEEDED,
    FAILED
}
