package com.victorbarbosa.fintracker.controller.dto;

import com.victorbarbosa.fintracker.enums.TransactionMethod;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionCreateResponse(
        Long id,
        String description,
        BigDecimal amount,
        TransactionMethod type,
        LocalDate date,
        Long categoryId
) {
}
