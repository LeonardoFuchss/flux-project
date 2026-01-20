package com.leoprojects.flux.dto;

import com.leoprojects.flux.domain.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
