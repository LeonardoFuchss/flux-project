package com.leoprojects.flux.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record TransactionRecordDTO(
        @NotBlank(message = "Title cannot be empty.") String title,
        @NotBlank(message = "Description cannot be empty.") String description,
        @NotNull(message = "Amount cannot be null.") BigDecimal amount,
        @NotNull(message = "Date cannot be null.") LocalDate date,
        @NotBlank(message = "Category cannot be empty.") String category
) {
}
