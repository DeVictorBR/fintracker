package com.victorbarbosa.fintracker.controller.dto;

import com.victorbarbosa.fintracker.enums.TransactionType;

public record CategoryUpdateResponse(
        Long id,
        String name,
        String description,
        TransactionType type
) {
}
