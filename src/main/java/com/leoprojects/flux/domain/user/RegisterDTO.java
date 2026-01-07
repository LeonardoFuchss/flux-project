package com.leoprojects.flux.domain.user;

public record RegisterDTO(String login, String password, UserRole role) {
}
