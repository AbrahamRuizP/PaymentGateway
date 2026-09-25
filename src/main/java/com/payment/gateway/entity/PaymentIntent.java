package com.payment.gateway.entity;

import com.payment.gateway.entity.enums.Currency;
import com.payment.gateway.entity.enums.PaymentIntentStatus;
import com.payment.gateway.exception.InvalidPaymentIntentTransitionException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"customer", "merchant", "payment"})
public class PaymentIntent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private BigDecimal amount = new BigDecimal("0.0");

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency = Currency.USD;

    @Setter(AccessLevel.NONE)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentIntentStatus status = PaymentIntentStatus.REQUIRES_PAYMENT_METHOD;

    @Column(columnDefinition = "TEXT")
    private String description = "";

    private Instant createdAt;
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @PrePersist
    private void prePersist() {
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

    public void transitionTo( PaymentIntentStatus newStatus ) {
        if (!isValidTransition(newStatus)) {
            throw new InvalidPaymentIntentTransitionException(
                    this.status,
                    newStatus,
                    id
            );
        }

        this.status = newStatus;
    }

    private boolean isValidTransition(PaymentIntentStatus newStatus) {
        return switch (status) {
            case REQUIRES_PAYMENT_METHOD ->
                    newStatus == PaymentIntentStatus.PROCESSING;

            case PROCESSING ->
                    newStatus == PaymentIntentStatus.SUCCEEDED
                            || newStatus == PaymentIntentStatus.FAILED;

            case SUCCEEDED, FAILED -> false;
        };
    }
}

