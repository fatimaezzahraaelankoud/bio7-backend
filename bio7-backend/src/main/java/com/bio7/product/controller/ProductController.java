package com.bio7.product.controller;

import com.bio7.product.dto.request.ProductRequest;
import com.bio7.product.dto.response.ProductResponse;
import com.bio7.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponse> create(
            @Valid @RequestBody ProductRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.create(request));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {

        return ResponseEntity.ok(
                productService.findAll()
        );
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                productService.findById(id)
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request
    ) {

        return ResponseEntity.ok(
                productService.update(id, request)
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        productService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
