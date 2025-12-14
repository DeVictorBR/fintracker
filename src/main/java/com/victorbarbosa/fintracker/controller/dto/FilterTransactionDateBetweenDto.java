package com.victorbarbosa.fintracker.controller.dto;

import java.time.LocalDate;

public record FilterTransactionDateBetweenDto(
        LocalDate start,
        LocalDate end
) {
}
