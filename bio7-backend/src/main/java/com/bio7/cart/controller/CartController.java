package com.bio7.cart.controller;

import com.bio7.cart.dto.request.AddToCartRequestDTO;
import com.bio7.cart.dto.request.UpdateCartItemRequestDTO;
import com.bio7.cart.dto.response.CartResponseDTO;
import com.bio7.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
    public class CartController {

        private final CartService cartService;

        @PostMapping("/items")
        public ResponseEntity<CartResponseDTO> addItem(
                @Valid @RequestBody AddToCartRequestDTO request
        ) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(cartService.addItem(request));
        }

        @GetMapping
        public ResponseEntity<CartResponseDTO> getCart() {
            return ResponseEntity.ok(cartService.getCart());
        }

    @PutMapping("/items/{productId}")
    public ResponseEntity<CartResponseDTO> updateItem(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateCartItemRequestDTO request
    ) {
        return ResponseEntity.ok(
                cartService.updateItem(productId, request)
        );
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartResponseDTO> removeItem(
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(
                cartService.removeItem(productId)
        );
    }

    }

