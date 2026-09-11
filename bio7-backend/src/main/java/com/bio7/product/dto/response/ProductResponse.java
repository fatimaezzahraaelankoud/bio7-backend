package com.bio7.product.dto.response;

import com.bio7.product.entity.Badge;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private String categoryName;
    private Long categoryID ;
    private Badge badge;
    private Integer stock;
    private boolean active;
}
