package com.victorbarbosa.fintracker.controller.dto;

import com.victorbarbosa.fintracker.enums.TransactionMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionCreateRequest(
        @NotNull
        @Size(min = 1, max = 100)
        String description,

        @NotNull
        @Positive
        BigDecimal amount,

        @NotNull
        TransactionMethod type,

        @NotNull
        LocalDate date,

        @NotNull
        Long categoryId
) {
}
