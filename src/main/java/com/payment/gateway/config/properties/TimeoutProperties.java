package com.payment.gateway.config.properties;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "payment-gateway.timeouts")
public record TimeoutProperties(

        @NotNull Duration connect,
        @NotNull Duration read,
        @NotNull Duration externalApi

) {}
