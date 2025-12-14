package com.victorbarbosa.fintracker.service;

import com.victorbarbosa.fintracker.base.PageResponse;
import com.victorbarbosa.fintracker.controller.dto.*;
import com.victorbarbosa.fintracker.entity.Transaction;
import com.victorbarbosa.fintracker.entity.User;
import com.victorbarbosa.fintracker.enums.TransactionMethod;
import com.victorbarbosa.fintracker.enums.TransactionType;
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

    public PageResponse<TransactionCreateResponse> findAllTransactionsByCategory(Long categoryId, Pageable pageable, Authentication auth) {
        var user = getAuthenticatedUser(auth);
        var page = transactionRepository.findByUserIdAndCategoryId(user.getId(), categoryId, pageable);
        var pageResponse = page.map(TransactionMapper::to);
        return PageMapper.toPageResponse(pageResponse);
    }

    public PageResponse<TransactionCreateResponse> findByUserIdAndDateBetween(FilterTransactionDateBetweenDto dto, Authentication auth, Pageable pageable) {
        var user = getAuthenticatedUser(auth);
        var page = transactionRepository.findByUserIdAndDateBetween(user.getId(), dto.start(), dto.end(), pageable);
        var pageResponse = page.map(TransactionMapper::to);
        return PageMapper.toPageResponse(pageResponse);
    }

    public PageResponse<TransactionCreateResponse> findByUserIdAndMethod(String method, Authentication auth, Pageable pageable) {
        var user = getAuthenticatedUser(auth);
        var methodCast = castingMethodWhenString(method);
        var page = transactionRepository.findByUserIdAndMethod(user.getId(), methodCast, pageable);
        var pageResponse = page.map(TransactionMapper::to);
        return PageMapper.toPageResponse(pageResponse);
    }

    public PageResponse<TransactionCreateResponse> findByUserIdAndCategoryType(String type, Authentication auth, Pageable pageable) {
        var user = getAuthenticatedUser(auth);
        var typeCast = castingTypeWhenString(type);
        var page = transactionRepository.findByUserIdAndCategory_Type(user.getId(), typeCast, pageable);
        var pageResponse = page.map(TransactionMapper::to);
        return PageMapper.toPageResponse(pageResponse);
    }

    public PageResponse<TransactionCreateResponse> findByUserIdAndMethodAndDateBetween(FilterTransactionMethodAndDateDto dto, Authentication auth, Pageable pageable) {
        var user = getAuthenticatedUser(auth);
        var methodCast = castingMethodWhenString(dto.method());
        var page = transactionRepository.findByUserIdAndMethodAndDateBetween(user.getId(), methodCast, dto.start(), dto.end(), pageable);
        var pageResponse = page.map(TransactionMapper::to);
        return PageMapper.toPageResponse(pageResponse);
    }

    public PageResponse<TransactionCreateResponse> findByUserIdAndAmountBetween(FilterTransactionAmountBetweenDto dto, Authentication auth, Pageable pageable) {
        var user = getAuthenticatedUser(auth);
        var page = transactionRepository.findByUserIdAndAmountBetween(user.getId(), dto.min(), dto.max(), pageable);
        var pageResponse = page.map(TransactionMapper::to);
        return PageMapper.toPageResponse(pageResponse);
    }

    private User getAuthenticatedUser(Authentication auth) {
        return userRepository.findByEmail(auth.getName()).orElseThrow();
    }

    private TransactionMethod castingMethodWhenString(String method) {
        var upper = method.toUpperCase();
        return TransactionMethod.valueOf(upper);
    }

    private TransactionType castingTypeWhenString(String type) {
        var upper = type.toUpperCase();
        return TransactionType.valueOf(upper);
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
