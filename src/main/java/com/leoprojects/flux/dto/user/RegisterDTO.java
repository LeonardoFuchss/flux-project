package com.leoprojects.flux.dto.user;

import com.leoprojects.flux.domain.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
