package com.victorbarbosa.fintracker.service;

import com.victorbarbosa.fintracker.base.PageResponse;
import com.victorbarbosa.fintracker.controller.dto.CategoryCreateRequest;
import com.victorbarbosa.fintracker.controller.dto.CategoryCreateResponse;
import com.victorbarbosa.fintracker.entity.User;
import com.victorbarbosa.fintracker.exception.CategoryNotFoundException;
import com.victorbarbosa.fintracker.mapper.CategoryMapper;
import com.victorbarbosa.fintracker.mapper.PageMapper;
import com.victorbarbosa.fintracker.repository.CategoryRepository;
import com.victorbarbosa.fintracker.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public CategoryCreateResponse create(CategoryCreateRequest req, Authentication auth) {
        var user = getAuthenticatedUser(auth);
        var category = CategoryMapper.from(req, user);
        var savedCategory = categoryRepository.save(category);
        return CategoryMapper.toResponse(savedCategory);
    }

    public PageResponse<CategoryCreateResponse> findAll(Pageable pageable, Authentication auth) {
        var user = getAuthenticatedUser(auth);
        var page = categoryRepository.findByUserId(user.getId(), pageable);
        var pageResp = page.map(CategoryMapper::toResponse);
        return PageMapper.toPageResponse(pageResp);
    }

    public CategoryCreateResponse findById(Long id, Authentication auth) {
        var user = getAuthenticatedUser(auth);
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id: " + id + " not found"));

        if (!category.getUser().getId().equals(user.getId())) {
            throw new CategoryNotFoundException("Category with id: " + id + " not found");
        }

        return CategoryMapper.toResponse(category);
    }

    private User getAuthenticatedUser(Authentication auth) {
        return userRepository.findByEmail(auth.getName()).orElseThrow();
    }
}
