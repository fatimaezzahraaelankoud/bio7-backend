package com.bio7.product.service;

import com.bio7.category.entity.Category;
import com.bio7.category.repository.CategoryRepository;
import com.bio7.product.dto.request.ProductRequest;
import com.bio7.product.dto.response.ProductResponse;
import com.bio7.product.entity.Product;
import com.bio7.product.mapper.ProductMapper;
import com.bio7.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldCreateProduct() {

        ProductRequest request = new ProductRequest();

        request.setName("Bio Shampoo");
        request.setDescription("Shampooing naturel");
        request.setPrice(new BigDecimal("49.90"));
        request.setImageUrl("https://example.com/shampoo.jpg");
        request.setCategoryId(1L);
        request.setBadge("NEW");
        request.setStock(10);

        when(productRepository.existsByNameIgnoreCase("Bio Shampoo"))
                .thenReturn(false);

        Category category = new Category();
        category.setId(1L);

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        Product product = Product.builder()
                .id(1L)
                .name("Bio Shampoo")
                .description("Shampooing naturel")
                .price(new BigDecimal("49.90"))
                .imageUrl("https://example.com/shampoo.jpg")
                .stock(10)
                .active(true)
                .category(category)
                .build();

        when(productMapper.toEntity(request, category))
                .thenReturn(product);

        when(productRepository.save(product))
                .thenReturn(product);

        ProductResponse response = ProductResponse.builder()
                .id(1L)
                .name("Bio Shampoo")
                .description("Shampooing naturel")
                .price(new BigDecimal("49.90"))
                .imageUrl("https://example.com/shampoo.jpg")
                .categoryName("Cosmétiques")
                .categoryId(1L)
                .stock(10)
                .active(true)
                .build();

        when(productMapper.toResponseDTO(product))
                .thenReturn(response);

        ProductResponse result = productService.create(request);

        assertEquals("Bio Shampoo", result.getName());
        assertEquals(new BigDecimal("49.90"), result.getPrice());
        assertEquals(10, result.getStock());
        assertEquals(1L, result.getCategoryId());
    }
}
