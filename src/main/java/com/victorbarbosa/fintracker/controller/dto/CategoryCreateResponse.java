package com.victorbarbosa.fintracker.controller.dto;

import com.victorbarbosa.fintracker.enums.TransactionType;

public record CategoryCreateResponse(
        Long id,
        String name,
        String description,
        TransactionType type
) {
}
