package com.victorbarbosa.fintracker.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FilterTransactionMethodAndDateDto(
        @NotBlank String method,
        @NotNull LocalDate start,
        @NotNull LocalDate end
) {
}
