package com.bio7.category.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {
    private Long id ;
    private String name ;
    private String description ;
    private boolean active ;
}
