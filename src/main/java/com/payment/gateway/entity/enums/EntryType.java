package com.payment.gateway.entity.enums;

import lombok.Getter;

@Getter
public enum EntryType {
    DEBIT( "DEBIT"),
    CREDIT( "CREDIT");

    private final String value;

    EntryType(String value) {
        this.value = value;
    }
}
