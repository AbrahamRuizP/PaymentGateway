package com.payment.gateway.controller.DTO;

import com.payment.gateway.entity.enums.MerchantStatus;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record MerchantResponse(
        UUID id,
        String businessName,
        String email,
        MerchantStatus status,
        Instant createdAt
) {
}
