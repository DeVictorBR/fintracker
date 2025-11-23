package com.victorbarbosa.fintracker.mapper;

import com.victorbarbosa.fintracker.controller.dto.CategoryCreateRequest;
import com.victorbarbosa.fintracker.controller.dto.CategoryCreateResponse;
import com.victorbarbosa.fintracker.entity.Category;
import com.victorbarbosa.fintracker.entity.User;

public class CategoryMapper {

    public static Category from(CategoryCreateRequest req, User user) {
        return new Category(
                req.name(),
                req.description(),
                user,
                req.type()
        );
    }

    public static CategoryCreateResponse toResponse(Category category) {
        return new CategoryCreateResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getType()
        );
    }
}
