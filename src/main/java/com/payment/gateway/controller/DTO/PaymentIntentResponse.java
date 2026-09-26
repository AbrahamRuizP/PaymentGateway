package com.payment.gateway.controller.DTO;

import com.payment.gateway.entity.enums.Currency;
import com.payment.gateway.entity.enums.PaymentIntentStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record PaymentIntentResponse(
        UUID id,
        BigDecimal amount,
        Currency currency,
        PaymentIntentStatus status,
        String description,
        Instant createdAt
) {}
