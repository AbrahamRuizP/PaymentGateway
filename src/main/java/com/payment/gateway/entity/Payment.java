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
@Table(name = "payment", indexes = {
        @Index(name = "idx_payment_merchant_id", columnList = "merchant_id"),
        @Index(name = "idx_payment_customer_id", columnList = "customer_id"),
        @Index(name = "idx_payment_status", columnList = "status"),
        @Index(name = "idx_payment_created_at", columnList = "created_at")
})
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Builder.Default
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal amount = BigDecimal.ZERO;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency = Currency.USD;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description = "";

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status = PaymentStatus.REQUIRES_PAYMENT_METHOD;

    @Version
    private int version;

    /* Dates */
    private Instant createdAt = null;
    private Instant updatedAt = null;

    /* External References */
    private UUID providerPaymentId = null;
    private String authorizationCode = "";
    private String acquirerReference = "";
    private String networkReference = "";

    /* Idempotency */
    @Column(name = "idempotency_key", nullable = false, unique = true)
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
        if ( status == null ) {
            status = PaymentStatus.REQUIRES_PAYMENT_METHOD;
        }
    }
}
