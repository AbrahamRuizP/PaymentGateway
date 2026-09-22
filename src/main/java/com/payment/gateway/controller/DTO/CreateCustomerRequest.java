package com.payment.gateway.controller.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCustomerRequest (
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotNull
        String description
) {}
