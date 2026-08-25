package com.payment.gateway.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

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
    }
}
