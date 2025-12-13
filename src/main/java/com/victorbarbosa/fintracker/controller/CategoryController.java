package com.victorbarbosa.fintracker.controller;

import com.victorbarbosa.fintracker.base.PageResponse;
import com.victorbarbosa.fintracker.controller.dto.CategoryCreateRequest;
import com.victorbarbosa.fintracker.controller.dto.CategoryCreateResponse;
import com.victorbarbosa.fintracker.controller.dto.CategoryUpdateRequest;
import com.victorbarbosa.fintracker.controller.dto.CategoryUpdateResponse;
import com.victorbarbosa.fintracker.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.victorbarbosa.fintracker.entity.Authority.Values.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + CATEGORY_CREATE + "')")
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
    @PreAuthorize("hasAuthority('" + CATEGORY_READ + "')")
    public ResponseEntity<PageResponse<CategoryCreateResponse>> findAll(Pageable pageable, Authentication auth) {
        var response = categoryService.findAll(pageable, auth);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('" + CATEGORY_READ + "')")
    public ResponseEntity<CategoryCreateResponse> findCategoryById(@PathVariable Long id, Authentication auth) {
        var response = categoryService.findById(id, auth);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('" + CATEGORY_UPDATE + "')")
    public ResponseEntity<CategoryUpdateResponse> updateCategory(@PathVariable Long id, Authentication auth,
                                                                 @RequestBody @Valid CategoryUpdateRequest req) {
        var response = categoryService.update(req, id, auth);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('" + CATEGORY_DELETE + "')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id, Authentication auth) {
        categoryService.delete(id, auth);
        return ResponseEntity.noContent().build();
    }
}
