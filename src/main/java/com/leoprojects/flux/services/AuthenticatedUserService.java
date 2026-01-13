package com.leoprojects.flux.services;

import com.leoprojects.flux.domain.user.User;
import com.leoprojects.flux.exceptions.FluxException;
import com.leoprojects.flux.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@AllArgsConstructor
@Service
public class AuthenticatedUserService {
    private final UserRepository repository;

    public User getAuthenticatedUser() {
        String login = Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                        .getName();

        User user = (User) repository.findByLogin(login);
        if (user == null) {
            throw new FluxException("Authenticated user not found");
        } else {
            return user;
        }
    }
}
