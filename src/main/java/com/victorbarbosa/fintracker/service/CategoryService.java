package com.victorbarbosa.fintracker.service;

import com.victorbarbosa.fintracker.base.PageResponse;
import com.victorbarbosa.fintracker.controller.dto.CategoryCreateRequest;
import com.victorbarbosa.fintracker.controller.dto.CategoryCreateResponse;
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
        var email = auth.getName();
        var user = userRepository.findByEmail(email).orElseThrow();
        var category = CategoryMapper.from(req, user);
        var savedCategory = categoryRepository.save(category);
        return CategoryMapper.toResponse(savedCategory);
    }

    public PageResponse<CategoryCreateResponse> findAll(Pageable pageable, Authentication auth) {
        var email = auth.getName();
        var user = userRepository.findByEmail(email).orElseThrow();
        var page = categoryRepository.findByUserId(user.getId(), pageable);
        var pageResp = page.map(CategoryMapper::toResponse);
        return PageMapper.toPageResponse(pageResp);
    }
}
