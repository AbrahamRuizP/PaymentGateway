package com.payment.gateway.exception;

import java.util.UUID;

public class MerchantNotFoundException extends RuntimeException {
    public MerchantNotFoundException(UUID id) {
        super("Merchant not found: " + id);
    }
}
