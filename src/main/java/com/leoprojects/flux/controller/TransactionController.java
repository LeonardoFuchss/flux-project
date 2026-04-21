package com.leoprojects.flux.controller;

import com.leoprojects.flux.dto.transaction.TransactionRequestDto;
import com.leoprojects.flux.dto.transaction.TransactionUpdateDto;
import com.leoprojects.flux.services.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
@CrossOrigin("*")
public class TransactionController {
    private final TransactionService service;

    @PostMapping()
    public ResponseEntity<?> register(@RequestBody @Valid TransactionRequestDto dto) {
        service.registerTransaction(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/summary/total-income")
    public ResponseEntity<?> totalIncome(@RequestParam int month, @RequestParam int year) {
        return ResponseEntity.ok(service.totalIncome(month, year));
    }

    @GetMapping("/summary/total-expense")
    public ResponseEntity<?> totalExpense(@RequestParam int month, @RequestParam int year) {
        return ResponseEntity.ok(service.totalExpense(month, year));
    }

    @GetMapping("/summary/current-balance")
    public ResponseEntity<?> currentBalance(@RequestParam int month, @RequestParam int year) {
        return ResponseEntity.ok(service.currentBalance(month, year));
    }

    @GetMapping()
    public ResponseEntity<?> findAllByUser(@RequestParam int month, @RequestParam int year) {
        return ResponseEntity.ok(service.findAllByUser(month, year));
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
