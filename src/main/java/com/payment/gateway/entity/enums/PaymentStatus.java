package com.payment.gateway.entity.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    REQUIRES_PAYMENT_METHOD("Requires Payment Method"),
    REQUIRES_CONFIRMATION("Requires Confirmation"),
    CAPTURED("Captured"),
    PARTIALLY_CAPTURED("Partially Captured"),
    CANCELED("Canceled"),
    FAILED("Failed"),
    REFUNDED("Refunded"),
    PARTIALLY_REFUNDED("Partially Refunded");

    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }
}
