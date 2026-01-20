package com.leoprojects.flux.controller;

import com.leoprojects.flux.domain.user.UserRole;
import com.leoprojects.flux.dto.AuthenticationDTO;
import com.leoprojects.flux.dto.LoginResponseDTO;
import com.leoprojects.flux.dto.PublicRegistrationDto;
import com.leoprojects.flux.dto.RegisterDTO;
import com.leoprojects.flux.domain.user.User;
import com.leoprojects.flux.exceptions.FluxException;
import com.leoprojects.flux.infra.security.TokenService;
import com.leoprojects.flux.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO dto) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        var auth = this.authenticationManager.authenticate(userNamePassword);
        var token = tokenService.generateToken((User) Objects.requireNonNull(auth.getPrincipal()));
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO dto) {
        if (this.repository.findByLogin(dto.login()) != null) {
            throw new FluxException("This user login is already in use. Please try again!");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        User user = new User(dto.login(), encryptedPassword, dto.role());
        this.repository.save(user);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/signup")
    public ResponseEntity<?> publicRegistration(@RequestBody @Valid PublicRegistrationDto dto) {
        if (this.repository.findByLogin(dto.login()) != null) {
            throw new FluxException("This user login is already in use. Please try again!");
        }
        if (!dto.password().equals(dto.confirmPassword())) {
            throw new FluxException("As senhas não correspondem. Tente novamente!");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        User user = new User(dto.login(), encryptedPassword, UserRole.USER);
        this.repository.save(user);
        return ResponseEntity.ok().build();
    }
}
