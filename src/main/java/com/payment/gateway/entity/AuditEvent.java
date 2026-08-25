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

    @Column(name = "entity_type")
    private EntityType entityType = null;

    @Column(name = "entity_id")
    private UUID entityId = null;

    private UUID actorId = null;
    private Instant createdAt = null;

    @PrePersist
    private void prePersist() {
        if ( id == null ) {
            id = UUID.randomUUID();
        }

        if ( createdAt == null ) {
            createdAt = Instant.now();
        }
    }
}
