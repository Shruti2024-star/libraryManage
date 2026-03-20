package com.libraryManagement.project1.mapper;

import com.libraryManagement.project1.dto.CategoryResponse;

import com.libraryManagement.project1.entities.Category;



import com.libraryManagement.project1.dto.*;
import com.libraryManagement.project1.entities.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryRequest request) {

        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public static CategoryResponse toResponse(Category category) {

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}