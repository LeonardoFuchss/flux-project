package com.leoprojects.flux.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record TransactionRequestDto(
        @NotBlank(message = "Description cannot be empty.")
        @Size(min = 3, max = 255, message = "Description must be between 3 and 255 characters.")
        String description,

        @NotNull(message = "Amount cannot be null.")
        @DecimalMin(value = "0.01", message = "The transaction amount must be greater than 0.")
        BigDecimal amount,

        @NotNull(message = "Date cannot be null.")
        LocalDate date,

        @NotNull(message = "Category cannot be null.")
        String category,

        @NotNull(message = "Type cannot be null.")
        String type
) {
}