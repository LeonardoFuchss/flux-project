package com.leoprojects.flux.domain.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {
    ADMIN("admin"),
    USER("user");

    private String role;

    @JsonCreator
    public UserRole fromValue(String role) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.role.equalsIgnoreCase(role) || userRole.name().equalsIgnoreCase(role)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("Role desconhecida: " + role);
    }

    UserRole(String role) {
        this.role = role;
    }

    @JsonValue
    public String getRole() {
        return role;
    }
}
