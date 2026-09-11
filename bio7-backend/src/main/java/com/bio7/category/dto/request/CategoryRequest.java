package com.bio7.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryRequest {

    @NotBlank(message = "Le nom de la catégorie est obligatoire")
    @Size(
            max = 50,
            message = "Le nom de la catégorie ne doit pas dépasser 50 caractères"
    )
    private String name;

    @Size(
            max = 500,
            message = "La description ne doit pas dépasser 500 caractères"
    )
    private String description;
}
