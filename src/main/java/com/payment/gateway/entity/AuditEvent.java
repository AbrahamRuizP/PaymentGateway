package com.payment.gateway.entity;

import com.payment.gateway.entity.enums.EntityType;
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
public class AuditEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "TEXT")
    private String description = "";

    private String eventType = "";

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type")
    private EntityType entityType;

    @Column(name = "entity_id")
    private UUID entityId;

    private UUID actorId;

    @Column(nullable = false)
    private Instant createdAt;

    @PrePersist
    private void prePersist() {
        if ( createdAt == null ) {
            createdAt = Instant.now();
        }
    }
}
