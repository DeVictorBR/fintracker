package com.victorbarbosa.fintracker.controller;

import com.victorbarbosa.fintracker.base.PageResponse;
import com.victorbarbosa.fintracker.controller.dto.CategoryCreateRequest;
import com.victorbarbosa.fintracker.controller.dto.CategoryCreateResponse;
import com.victorbarbosa.fintracker.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryCreateResponse> create(@RequestBody @Valid CategoryCreateRequest req, Authentication auth) {
        var response = categoryService.create(req, auth);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<CategoryCreateResponse>> findAll(Pageable pageable, Authentication auth) {
        var response = categoryService.findAll(pageable, auth);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryCreateResponse> findCategoryById(@PathVariable Long id, Authentication auth) {
        var response = categoryService.findById(id, auth);
        return ResponseEntity.ok(response);
    }
}
