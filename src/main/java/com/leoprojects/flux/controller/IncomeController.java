package com.leoprojects.flux.controller;

import com.leoprojects.flux.dto.TransactionRecordDTO;
import com.leoprojects.flux.services.IncomeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("income")
@AllArgsConstructor
public class IncomeController {
    private final IncomeService service;

    @PostMapping()
    public ResponseEntity<?> register(@RequestBody @Valid TransactionRecordDTO dto) {
        service.registerIncome(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
