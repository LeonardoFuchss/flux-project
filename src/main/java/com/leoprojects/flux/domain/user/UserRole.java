package com.leoprojects.flux.domain.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.leoprojects.flux.exceptions.FluxException;

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
        throw new FluxException("Unknown role: " + role);
    }

    UserRole(String role) {
        this.role = role;
    }

    @JsonValue
    public String getRole() {
        return role;
    }
}
