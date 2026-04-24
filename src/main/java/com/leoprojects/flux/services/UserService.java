package com.leoprojects.flux.services;

import com.leoprojects.flux.domain.user.User;
import com.leoprojects.flux.domain.user.UserRole;
import com.leoprojects.flux.dto.user.AuthenticationDTO;
import com.leoprojects.flux.dto.user.LoginResponseDTO;
import com.leoprojects.flux.dto.user.PublicRegistrationDto;
import com.leoprojects.flux.exceptions.FluxException;
import com.leoprojects.flux.infra.security.TokenService;
import com.leoprojects.flux.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public void createUser(String login, String password, UserRole role) {
        this.validateLogin(login);
        String encryptedPassword = passwordEncoder.encode(password);
        this.repository.save(new User(login, encryptedPassword, role));
    }
    public void createPublicUser(PublicRegistrationDto dto) {
        this.validateLogin(dto.login());
        this.validatePassword(dto.password(), dto.confirmPassword());
        String encryptedPassword = passwordEncoder.encode(dto.password());
        this.repository.save(new User(dto.login(), encryptedPassword, UserRole.USER));
    }
    public LoginResponseDTO userLogin(AuthenticationDTO dto) {
        try {
            var userNamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
            var auth = this.getAuthentication(userNamePassword);
            return new LoginResponseDTO(this.getToken((User) auth.getPrincipal()));

        } catch (Exception e) {
            throw new FluxException("Invalid credentials");
        }
    }



    private void validateLogin(String login) {
        if (this.repository.findByLogin(login) != null) {
            throw new FluxException("This user login is already in use. Please try again!");
        }
    }
    private Authentication getAuthentication(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        return this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }
    private String getToken(User user) {
        return tokenService.generateToken(user);
    }
    private void validatePassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new FluxException("The passwords do not match. Try again!");
        }
    }
}
