package com.leoprojects.flux.dto.transaction;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record TransactionRequestDto(
        @NotBlank(message = "The description is mandatory.")
        @Size(min = 3, max = 255, message = "Description must be between 3 and 255 characters.")
        String description,

        @NotNull(message = "The amount is mandatory.")
        @DecimalMin(value = "0.01", message = "The transaction amount must be greater than 0.")
        BigDecimal amount,

        @NotNull(message = "The date is mandatory.")
        LocalDate date,

        @NotNull(message = "The category is mandatory.")
        String category,

        @NotNull(message = "The type is mandatory.")
        String type
) {
}