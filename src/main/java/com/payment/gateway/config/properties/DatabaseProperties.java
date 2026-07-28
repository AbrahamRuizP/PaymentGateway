package com.payment.gateway.config.properties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "payment-gateway.database")
public record DatabaseProperties(

        @NotBlank String schema,
        @NotBlank String auditSchema,
        @Min(1) int defaultPageSize

) {}
