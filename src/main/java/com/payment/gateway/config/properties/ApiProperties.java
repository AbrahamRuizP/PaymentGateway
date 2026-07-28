package com.payment.gateway.config.properties;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "payment-gateway.api")
public record ApiProperties (

        @NotBlank String version,
        @NotBlank String basePath

) {}
