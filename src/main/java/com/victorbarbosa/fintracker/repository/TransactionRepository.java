package com.victorbarbosa.fintracker.repository;

import com.victorbarbosa.fintracker.entity.Transaction;
import com.victorbarbosa.fintracker.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByUserId(Long userId, Pageable pageable);

    Page<Transaction> findByUserIdAndCategoryId(Long userId, Long categoryId, Pageable pageable);

    Page<Transaction> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end, Pageable pageable);

    Page<Transaction> findByUserIdAndType(Long userId, TransactionType type, Pageable pageable);

    Page<Transaction> findByUserIdAndTypeAndDateBetween(
            Long userId,
            TransactionType type,
            LocalDate start,
            LocalDate end,
            Pageable pageable
    );

    Page<Transaction> findByUserIdAndAmountBetween(
            Long userId,
            BigDecimal min,
            BigDecimal max,
            Pageable pageable
    );

    Page<Transaction> findByUserIdAndDateBetweenAndAmountBetween(
            Long userId,
            LocalDate start,
            LocalDate end,
            BigDecimal min,
            BigDecimal max,
            Pageable pageable
    );

    Page<Transaction> findByUserIdAndCategoryIdAndDateBetween(
            Long userId,
            Long categoryId,
            LocalDate start,
            LocalDate end,
            Pageable pageable
    );
}
