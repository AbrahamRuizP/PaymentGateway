package com.payment.gateway.config.properties;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "payment-gateway.rate-limit")
public record RateLimitProperties (

        @Min(1) int merchantRequestsPerMinute,
        @Min(1) int publicRequestsPerMinute

) {}
