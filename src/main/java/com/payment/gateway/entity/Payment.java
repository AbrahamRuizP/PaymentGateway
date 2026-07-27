package com.payment.gateway.entity;

import com.payment.gateway.entity.enums.Currency;
import com.payment.gateway.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Payment {

    @Id
    @Column(name = "id")
    private UUID id;

    private double amount = 0.0;

    @Enumerated
    @Column(name = "currency")
    private Currency currency = Currency.USD;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description = "";

    @Enumerated
    @Column(name = "status")
    private PaymentStatus status = PaymentStatus.REQUIRES_PAYMENT_METHOD;

    /* Dates */
    private LocalDate createdAt = null;
    private LocalDate updatedAt = null;

    /* External References */
    private UUID providerPaymentId = null;
    private String authorizationCode = "";
    private String acquirerReference = "";
    private String networkReference = "";

    /* Idempotency */
    private String idempotencyKey = "";

    /* Relationships */
    @ToString.Exclude
    @OneToMany
    @Column(name = "merchant_id")
    private Merchant merchant;

    @ToString.Exclude
    @OneToMany
    @Column(name = "customer_id")
    private Customer customer;

    @PrePersist
    private void prePersist() {
        if ( id == null ) {
            id = UUID.randomUUID();
        }
    }
}
