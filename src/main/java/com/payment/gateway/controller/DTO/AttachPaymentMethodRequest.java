package com.payment.gateway.controller.DTO;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AttachPaymentMethodRequest (
        @NotNull
        UUID paymentMethodId
) {}
