package com.leoprojects.flux.controller;

import com.leoprojects.flux.dto.TransactionRequestDto;
import com.leoprojects.flux.dto.TransactionUpdateDto;
import com.leoprojects.flux.services.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService service;

    @PostMapping()
    public ResponseEntity<?> register(@RequestBody @Valid TransactionRequestDto dto) {
        service.registerIncome(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/summary/total-income")
    public ResponseEntity<?> totalIncome() {
        return ResponseEntity.ok(service.totalIncome());
    }

    @GetMapping("/summary/total-expense")
    public ResponseEntity<?> totalExpense() {
        return ResponseEntity.ok(service.totalExpense());
    }

    @GetMapping("/summary/current-balance")
    public ResponseEntity<?> currentBalance() {
        return ResponseEntity.ok(service.currentBalance());
    }

    @GetMapping()
    public ResponseEntity<?> findAllByUser() {
        return ResponseEntity.ok(service.findAllByUser());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTransaction(@RequestBody @Valid TransactionUpdateDto dto, @PathVariable Long id) {
        service.updateTransaction(dto, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransactionById(@PathVariable Long id) {
        service.deleteTransaction(id);
        return ResponseEntity.ok().build();
    }
}
