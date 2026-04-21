package com.leoprojects.flux.dto.transaction;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record TransactionResponseDto(String type, BigDecimal amount, LocalDate date, String category, String description) {
}
