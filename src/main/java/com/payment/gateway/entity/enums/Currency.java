package com.payment.gateway.entity.enums;

import lombok.Getter;

@Getter
public enum Currency {
    USD(0, "USD"),
    EUR(1, "EUR");

    private final int value;
    private final String displayValue;

    Currency(int value, String displayValue) {
        this.displayValue = displayValue;
        this.value = value;
    }
}
