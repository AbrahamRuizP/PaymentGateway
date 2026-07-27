package com.payment.gateway.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
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

    @PrePersist
    private void prePersist() {
        if ( id == null ) {
            id = UUID.randomUUID();
        }
    }
}
