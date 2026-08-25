package com.payment.gateway.entity;

import com.payment.gateway.entity.enums.Currency;
import com.payment.gateway.entity.enums.PaymentStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"customer", "merchant", "payment"})
@Builder
public class PaymentIntent {

    @Id
    @Column(name = "id")
    private UUID id;

    private BigDecimal amount = new BigDecimal("0.0");

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency = Currency.USD;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status = PaymentStatus.REQUIRES_PAYMENT_METHOD;

    @Column(columnDefinition = "TEXT")
    private String description = "";

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Column(name = "created_at")
    private Instant createdAt = null;

    @Column(name = "updated_at")
    private Instant updatedAt = null;

    @PrePersist
    private void prePersist() {
        if ( id == null ) {
            id = UUID.randomUUID();
        }

        if (createdAt == null) {
            createdAt = Instant.now();
        }

        if (updatedAt == null) {
            updatedAt = createdAt;
        }
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = Instant.now();
    }
}
