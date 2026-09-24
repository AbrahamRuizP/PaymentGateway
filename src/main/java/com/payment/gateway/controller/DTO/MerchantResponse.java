package com.payment.gateway.controller.DTO;

import com.payment.gateway.entity.enums.MerchantStatus;

import java.time.Instant;
import java.util.UUID;

public record MerchantResponse(
        UUID id,
        String businessName,
        String email,
        MerchantStatus status,
        Instant createdAt
) {
}
