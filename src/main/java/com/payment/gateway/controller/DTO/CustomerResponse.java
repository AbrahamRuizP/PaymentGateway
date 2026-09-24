package com.payment.gateway.controller.DTO;

import java.time.Instant;
import java.util.UUID;

public record CustomerResponse (
        UUID id,
        String firstName,
        String lastName,
        Instant createdAt,
        Instant updatedAt
) {}
