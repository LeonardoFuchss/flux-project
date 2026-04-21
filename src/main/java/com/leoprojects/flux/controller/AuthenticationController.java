package com.leoprojects.flux.controller;

import com.leoprojects.flux.dto.user.AuthenticationDTO;
import com.leoprojects.flux.dto.user.PublicRegistrationDto;
import com.leoprojects.flux.dto.user.RegisterDTO;
import com.leoprojects.flux.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
