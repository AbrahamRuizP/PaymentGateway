package com.payment.gateway.entity.enums;

public enum UserRole {
    SUPER_ADMIN, // 0
    ADMIN, // 1
    USER, // 2
    CLIENT; // 3

    public String asRole() {
        return "ROLE_" + this.name();
    }
}
