package com.payment.gateway.entity;

import com.payment.gateway.entity.enums.MerchantStatus;
import com.payment.gateway.service.MerchantService;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "business_name")
    private String businessName = "";
    private String email = "";

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MerchantStatus status = MerchantStatus.CREATED;

    private Instant createdAt = null;
    private Instant updatedAt = null;

    @PrePersist
    private void prePersist() {
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
