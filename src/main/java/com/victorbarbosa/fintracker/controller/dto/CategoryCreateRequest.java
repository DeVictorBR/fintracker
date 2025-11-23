package com.victorbarbosa.fintracker.controller.dto;

import com.victorbarbosa.fintracker.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryCreateRequest(
        @NotBlank @Size(max = 50) String name,
        String description,
        @NotNull TransactionType type) {
}
