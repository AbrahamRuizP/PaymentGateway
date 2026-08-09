package com.payment.gateway.entity;

import com.payment.gateway.entity.enums.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Ledger {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private BigDecimal amount = new BigDecimal("0.0");

    @Enumerated(EnumType.ORDINAL)
    private Currency currency = Currency.USD;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description = "";

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer = null;

    @PrePersist
    private void prePersist() {
        if ( id == null ) {
            id = UUID.randomUUID();
        }
    }
}
