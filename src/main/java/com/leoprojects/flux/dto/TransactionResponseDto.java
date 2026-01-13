package com.leoprojects.flux.dto;

import lombok.Builder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record TransactionResponseDto(String type, BigDecimal amount, LocalDate date, String category, String description) {
}
