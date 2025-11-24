package com.victorbarbosa.fintracker.service;

import com.victorbarbosa.fintracker.base.PageResponse;
import com.victorbarbosa.fintracker.controller.dto.TransactionCreateRequest;
import com.victorbarbosa.fintracker.controller.dto.TransactionCreateResponse;
import com.victorbarbosa.fintracker.entity.Transaction;
import com.victorbarbosa.fintracker.entity.User;
import com.victorbarbosa.fintracker.exception.TransactionNotFoundException;
import com.victorbarbosa.fintracker.mapper.PageMapper;
import com.victorbarbosa.fintracker.mapper.TransactionMapper;
import com.victorbarbosa.fintracker.repository.TransactionRepository;
import com.victorbarbosa.fintracker.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public TransactionCreateResponse create(TransactionCreateRequest req, Authentication auth) {
        var user = getAuthenticatedUser(auth);
        var category = categoryService.getCategoryByIdAndUser(req.categoryId(), user);
        var transaction = TransactionMapper.from(req, user, category);
        var savedTransaction = transactionRepository.save(transaction);
        return TransactionMapper.to(savedTransaction);
    }

    public PageResponse<TransactionCreateResponse> findAll(Authentication auth, Pageable pageable) {
        var user = getAuthenticatedUser(auth);
        var page = transactionRepository.findByUserId(user.getId(), pageable);
        var pageResponse = page.map(TransactionMapper::to);
        return PageMapper.toPageResponse(pageResponse);
    }

    public TransactionCreateResponse findById(Long id, Authentication auth) {
        var user = getAuthenticatedUser(auth);
        var transaction = getTransactionByIdAndUser(id, user);
        return TransactionMapper.to(transaction);
    }

    private User getAuthenticatedUser(Authentication auth) {
        return userRepository.findByEmail(auth.getName()).orElseThrow();
    }

    private Transaction getTransactionByIdAndUser(Long id, User user) {
        var transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction with id: " + id + " not found"));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new TransactionNotFoundException("Transaction with id: " + id + " not found");
        }
        return transaction;
    }
}
