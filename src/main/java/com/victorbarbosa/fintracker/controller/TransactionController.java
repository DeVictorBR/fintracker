package com.victorbarbosa.fintracker.controller;

import com.victorbarbosa.fintracker.base.PageResponse;
import com.victorbarbosa.fintracker.controller.dto.TransactionCreateRequest;
import com.victorbarbosa.fintracker.controller.dto.TransactionCreateResponse;
import com.victorbarbosa.fintracker.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

import static com.victorbarbosa.fintracker.entity.Authority.Values.TRANSACTION_CREATE;
import static com.victorbarbosa.fintracker.entity.Authority.Values.TRANSACTION_READ;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + TRANSACTION_CREATE + "')")
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
    @PreAuthorize("hasAuthority('" + TRANSACTION_READ + "')")
    public ResponseEntity<PageResponse<TransactionCreateResponse>> findAllTransactions(Authentication auth, Pageable pageable) {
        var response = transactionService.findAll(auth, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('" + TRANSACTION_READ + "')")
    public ResponseEntity<TransactionCreateResponse> findTransactionById(@PathVariable Long id, Authentication auth) {
        var response = transactionService.findById(id, auth);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{id}")
    @PreAuthorize("hasAuthority('" + TRANSACTION_READ + "')")
    public ResponseEntity<PageResponse<TransactionCreateResponse>> findAllTransactionsByCategory(@PathVariable Long id, Authentication auth, Pageable pageable) {
        var response = transactionService.findAllTransactionsByCategory(id, pageable, auth);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/period")
    @PreAuthorize("hasAuthority('" + TRANSACTION_READ + "')")
    public ResponseEntity<PageResponse<TransactionCreateResponse>> findByUserIdAndDateBetween(@RequestParam LocalDate start, @RequestParam LocalDate end, Authentication auth, Pageable pageable) {
        var response = transactionService.findByUserIdAndDateBetween(start, end, auth, pageable);
        return ResponseEntity.ok(response);
    }
}
