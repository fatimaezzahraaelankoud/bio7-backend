package com.bio7.product.dto.request;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {

    @NotBlank(message = "le nom du produit est obligatoire ")
    @Size(max=50, message = "Le nom ne doit pas dépasser 50 caractères")
    private  String name ;

    @Size(max = 1000, message = "La description ne doit pas dépasser 1000 caractères")
    private String description;

    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.01", message = "Le prix doit être supérieur à 0")
    @Digits(integer = 8, fraction = 2, message = "Le prix doit avoir au maximum 2 décimales")
    private BigDecimal price;

    @NotBlank(message = "L'image est obligatoire")
    @Size(max = 250, message = "L'URL de l'image ne doit pas dépasser 250 caractères")
    private String imageUrl;

    @NotNull(message = "La catégorie est obligatoire")
    private Long categoryId;

    private String badge;

    @NotNull(message = "Le stock est obligatoire")
    @Min(value = 0, message = "Le stock ne peut pas être négatif")
    private Integer stock;
}
