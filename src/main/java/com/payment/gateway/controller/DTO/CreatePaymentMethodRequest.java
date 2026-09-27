package com.payment.gateway.controller.DTO;

import com.payment.gateway.entity.enums.PaymentMethodType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreatePaymentMethodRequest(
        @NotNull
        PaymentMethodType type,

        @NotBlank
        String provider,

        // both fields can be null
        String brand,
        String last4,

        @NotBlank
        String providerPaymentMethodId,

        @NotNull
        UUID customerId
) {}
