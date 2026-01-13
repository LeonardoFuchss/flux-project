package com.leoprojects.flux.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record TransactionUpdateDto(
        @Size(min = 3, max = 255, message = "Description must be between 3 and 255 characters.") String description,
        @DecimalMin(value = "0.01", message = "The transaction amount must be greater than 0.") BigDecimal amount,
        LocalDate date,
        String category,
        String type
) {
}
