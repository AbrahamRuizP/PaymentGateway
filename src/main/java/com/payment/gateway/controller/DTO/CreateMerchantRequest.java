package com.payment.gateway.controller.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateMerchantRequest(
        @NotBlank
        String businessName,

        @NotBlank
        @Email
        String email
) {}
