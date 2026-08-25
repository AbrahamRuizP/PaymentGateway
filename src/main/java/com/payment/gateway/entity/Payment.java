package com.payment.gateway.entity;

import com.payment.gateway.entity.enums.Currency;
import com.payment.gateway.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"customer", "merchant"})
@Getter
@Setter
public class Payment {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal amount = new BigDecimal("0.0");

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency = Currency.USD;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description = "";

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status = PaymentStatus.REQUIRES_PAYMENT_METHOD;

    /* Dates */
    private Instant createdAt = null;
    private Instant updatedAt = null;

    /* External References */
    private UUID providerPaymentId = null;
    private String authorizationCode = "";
    private String acquirerReference = "";
    private String networkReference = "";

    /* Idempotency */
    @Column(name = "idempotency_key")
    private String idempotencyKey = "";

    /* Relationships */
    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @PrePersist
    private void prePersist() {
        if ( id == null ) {
            id = UUID.randomUUID();
        }
        if ( status == null ) {
            status = PaymentStatus.REQUIRES_PAYMENT_METHOD;
        }
    }
}
