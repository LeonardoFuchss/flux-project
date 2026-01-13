package com.leoprojects.flux.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record TransactionUpdateDto(
        String description,
        BigDecimal amount,
        LocalDate date,
        String category,
        String type
) {
}
