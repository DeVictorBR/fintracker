package com.victorbarbosa.fintracker.service;

import com.victorbarbosa.fintracker.controller.dto.TransactionCreateRequest;
import com.victorbarbosa.fintracker.controller.dto.TransactionCreateResponse;
import com.victorbarbosa.fintracker.entity.User;
import com.victorbarbosa.fintracker.mapper.TransactionMapper;
import com.victorbarbosa.fintracker.repository.TransactionRepository;
import com.victorbarbosa.fintracker.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, CategoryService categoryService) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.categoryService = categoryService;
    }

    public TransactionCreateResponse create(TransactionCreateRequest req, Authentication auth) {
        var user = getAuthenticatedUser(auth);
        var category = categoryService.getCategoryByIdAndUser(req.categoryId(), user);
        var transaction = TransactionMapper.from(req, user, category);
        var savedTransaction = transactionRepository.save(transaction);
        return TransactionMapper.to(savedTransaction);
    }

    private User getAuthenticatedUser(Authentication auth) {
        return userRepository.findByEmail(auth.getName()).orElseThrow();
    }
}
