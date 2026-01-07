package com.leoprojects.flux.controller;

import com.leoprojects.flux.domain.user.AuthenticationDTO;
import com.leoprojects.flux.domain.user.LoginResponseDTO;
import com.leoprojects.flux.domain.user.RegisterDTO;
import com.leoprojects.flux.domain.user.User;
import com.leoprojects.flux.infra.security.TokenService;
import com.leoprojects.flux.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
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
        if (this.repository.findByLogin(dto.login()) != null) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        User user = new User(dto.login(), encryptedPassword, dto.role());
        this.repository.save(user);
        return ResponseEntity.ok().build();
    }
}
