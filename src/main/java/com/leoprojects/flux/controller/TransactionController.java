package com.leoprojects.flux.controller;

import com.leoprojects.flux.dto.TransactionRequestDto;
import com.leoprojects.flux.services.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("transaction")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService service;

    @PostMapping()
    public ResponseEntity<?> register(@RequestBody @Valid TransactionRequestDto dto) {
        service.registerIncome(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/totalIncome")
    public ResponseEntity<?> totalIncome() {
        return ResponseEntity.ok(service.totalIncome());
    }

    @GetMapping("/totalExpense")
    public ResponseEntity<?> totalExpense() {
        return ResponseEntity.ok(service.totalExpense());
    }

    @GetMapping("/currentBalance")
    public ResponseEntity<?> currentBalance() {
        return ResponseEntity.ok(service.currentBalance());
    }

    @GetMapping()
    public ResponseEntity<?> findAllByUser() {
        return ResponseEntity.ok(service.findAllByUser());
    }
}
