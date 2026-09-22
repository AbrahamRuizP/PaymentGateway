package com.payment.gateway.controller.DTO;

import java.time.Instant;
import java.util.UUID;

public record CustomerResponse (
        UUID id,
        String lastName,
        String firstName,
        Instant createdAt,
        Instant updatedAt
) {}
