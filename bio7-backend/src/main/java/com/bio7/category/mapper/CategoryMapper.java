package com.bio7.category.mapper;

import com.bio7.category.dto.request.CategoryRequest;
import com.bio7.category.dto.response.CategoryResponse;
import com.bio7.category.entity.Category;

public class CategoryMapper {

    public CategoryResponse toResponse(Category category){
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .active(category.isActive())
                .build();
    }

    public Category toEntity(CategoryRequest request){

        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .active(true)
                .build();
    }


}
