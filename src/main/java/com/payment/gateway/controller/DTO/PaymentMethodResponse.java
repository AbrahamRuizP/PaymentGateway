package com.payment.gateway.controller.DTO;

import com.payment.gateway.entity.enums.PaymentMethodType;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record PaymentMethodResponse(
        UUID id,
        PaymentMethodType type,
        String provider,
        Instant createdAt
) {}
