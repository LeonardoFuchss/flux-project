package com.leoprojects.flux.controller;

import com.leoprojects.flux.domain.user.User;
import com.leoprojects.flux.domain.user.UserRole;
import com.leoprojects.flux.dto.AuthenticationDTO;
import com.leoprojects.flux.dto.LoginResponseDTO;
import com.leoprojects.flux.dto.PublicRegistrationDto;
import com.leoprojects.flux.dto.RegisterDTO;
import com.leoprojects.flux.exceptions.FluxException;
import com.leoprojects.flux.repository.UserRepository;
import com.leoprojects.flux.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO dto) {
        return ResponseEntity.ok(userService.userLogin(dto));
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO dto) {
        userService.createdUser(dto.login(), dto.password(), dto.role());
        return ResponseEntity.ok().build();
    }
    @PostMapping("/signup")
    public ResponseEntity<?> publicRegistration(@RequestBody @Valid PublicRegistrationDto dto) {
        userService.createPublicUser(dto);
        return ResponseEntity.ok().build();
    }
}
