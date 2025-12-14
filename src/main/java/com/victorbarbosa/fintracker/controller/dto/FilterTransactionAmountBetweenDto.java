package com.victorbarbosa.fintracker.controller.dto;

import java.math.BigDecimal;

public record FilterTransactionAmountBetweenDto(
        BigDecimal min,
        BigDecimal max
) {
}
