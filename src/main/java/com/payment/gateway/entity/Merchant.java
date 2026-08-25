package com.payment.gateway.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Merchant {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "business_name")
    private String businessName = "";
    private String email = "";
    private String status = "";

    private Instant createdAt = null;
    private Instant updatedAt = null;

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
