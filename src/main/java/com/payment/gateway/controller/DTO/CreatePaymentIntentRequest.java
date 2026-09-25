package com.payment.gateway.controller.DTO;

import com.payment.gateway.entity.enums.Currency;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePaymentIntentRequest (
        @NotNull
        BigDecimal amount,

        @NotNull
        Currency currency,

        String description,

        @NotNull
        UUID merchantId,

        @NotNull
        UUID customerId
) {}
