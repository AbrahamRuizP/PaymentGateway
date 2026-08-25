package com.payment.gateway.entity.enums;

import lombok.Getter;

@Getter
public enum EntityType {
    CUSTOMER( "CUSTOMER"),
    LEDGER( "LEDGER"),
    MERCHANT( "MERCHANT"),
    PAYMENT( "PAYMENT"),
    PAYMENT_INTENT( "PAYMENT"),
    USER( "USER");

    private final String value;

    EntityType(String value) {
        this.value = value;
    }
}
