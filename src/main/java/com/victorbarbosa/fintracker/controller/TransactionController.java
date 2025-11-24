package com.victorbarbosa.fintracker.controller;

import com.victorbarbosa.fintracker.base.PageResponse;
import com.victorbarbosa.fintracker.controller.dto.TransactionCreateRequest;
import com.victorbarbosa.fintracker.controller.dto.TransactionCreateResponse;
import com.victorbarbosa.fintracker.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionCreateResponse> create(@RequestBody @Valid TransactionCreateRequest req, Authentication auth) {
        var response = transactionService.create(req, auth);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<TransactionCreateResponse>> findAllTransactions(Authentication auth, Pageable pageable) {
        var response = transactionService.findAll(auth, pageable);
        return ResponseEntity.ok(response);
    }
}
