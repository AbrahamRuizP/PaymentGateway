package com.payment.gateway.entity;

import com.payment.gateway.entity.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Users implements UserDetails {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "first_name")
    private String firstName = "";

    @Column(name = "last_name")
    private String lastName = "";

    @Column(name = "account_non_expired")
    private boolean accountNonExpired;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;

    private boolean enabled;

    private String email = "";

    @Column(name = "reset_token")
    private String resetToken = "";

    @NotNull
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private UserRole userRole;

    @Column(name = "password_hash")
    private String passwordHash = "";

    private String phone = "";

    @Column(name = "username", unique = true)
    private String username = "";

    @PrePersist
    private void prePersist() {
        if ( id == null ) {
            id = UUID.randomUUID();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.asRole()));
    }

    @Override
    public String getPassword() { return passwordHash; }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
