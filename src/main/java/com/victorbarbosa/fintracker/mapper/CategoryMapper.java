package com.victorbarbosa.fintracker.mapper;

import com.victorbarbosa.fintracker.controller.dto.CategoryCreateRequest;
import com.victorbarbosa.fintracker.controller.dto.CategoryCreateResponse;
import com.victorbarbosa.fintracker.controller.dto.CategoryUpdateResponse;
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

    public static CategoryCreateResponse to(Category category) {
        return new CategoryCreateResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getType()
        );
    }

    public static CategoryUpdateResponse toUp(Category category) {
        return new CategoryUpdateResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getType()
        );
    }
}
