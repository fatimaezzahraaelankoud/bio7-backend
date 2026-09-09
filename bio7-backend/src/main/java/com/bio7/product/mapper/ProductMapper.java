package com.bio7.product.mapper;

import com.bio7.product.dto.request.ProductRequest;
import com.bio7.product.dto.response.ProductResponse;
import com.bio7.product.entity.Badge;
import com.bio7.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest dto){
        return Product.builder().
                name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .imageUrl(dto.getImageUrl())
                .category(dto.getCategory())
                .badge(parseBadge(dto.getBadge()))
                .stock(dto.getStock())
                .active(true)
                .build();
    }

    public ProductResponse toResponseDTO(Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .category(product.getCategory())
                .badge(product.getBadge())
                .stock(product.getStock())
                .active(product.isActive())
                .build();
    }


    public Badge parseBadge(String badge){

        if (badge == null || badge.isBlank()) {
            return null;
        }

        return Badge.valueOf(badge.toUpperCase());

    }

}
