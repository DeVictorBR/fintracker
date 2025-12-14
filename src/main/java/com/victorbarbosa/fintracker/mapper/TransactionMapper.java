package com.victorbarbosa.fintracker.mapper;

import com.victorbarbosa.fintracker.controller.dto.TransactionCreateRequest;
import com.victorbarbosa.fintracker.controller.dto.TransactionCreateResponse;
import com.victorbarbosa.fintracker.entity.Category;
import com.victorbarbosa.fintracker.entity.Transaction;
import com.victorbarbosa.fintracker.entity.User;

public class TransactionMapper {

    public static Transaction from(TransactionCreateRequest req, User user, Category category) {
        return new Transaction(
                req.description(),
                req.amount(),
                req.method(),
                req.date(),
                category,
                user
        );
    }

    public static TransactionCreateResponse to(Transaction transaction) {
        return new TransactionCreateResponse(
                transaction.getId(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getMethod(),
                transaction.getDate(),
                transaction.getCategory().getId()
        );
    }
}
