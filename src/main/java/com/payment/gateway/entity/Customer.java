package com.payment.gateway.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"ledgers", "payments", "paymentIntents"})
@Getter
@Setter
public class Customer {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "created_at")
    private Instant createdAt = null;
    @Column(name = "updated_at")
    private Instant updatedAt = null;

    @NotEmpty
    private String firstName = "";
    @NotEmpty
    private String lastName = "";

    @Column(columnDefinition = "TEXT")
    private String description = "";
    
    @Transient
    public String getFullName() { return firstName + " " + lastName; }

    /* Relationships */
    @OneToMany(mappedBy = "customer")
    private Set<Ledger> ledgers = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<PaymentIntent> paymentIntents = new HashSet<>();

    @PrePersist
    private void prePersist() {
        if ( id == null ) {
            id = UUID.randomUUID();
        }

        if ( createdAt == null ) {
            createdAt = Instant.now();
        }

        if ( updatedAt == null ) {
            updatedAt = createdAt;
        }
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = Instant.now();
    }
}
