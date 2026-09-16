package com.bio7.cart.mapper;


import com.bio7.cart.dto.response.CartItemResponseDTO;
import com.bio7.cart.dto.response.CartResponseDTO;
import com.bio7.cart.entity.Cart;
import com.bio7.cart.entity.CartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CartMapper {

    public CartItemResponseDTO toItemResponse(CartItem item) {

        BigDecimal subtotal = item.getProduct()
                .getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));

        return CartItemResponseDTO.builder()
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .unitPrice(item.getProduct().getPrice())
                .quantity(item.getQuantity())
                .subtotal(subtotal)
                .build();
    }

    public CartResponseDTO toResponse(Cart cart) {

        List<CartItemResponseDTO> items = cart.getItems()
                .stream()
                .map(this::toItemResponse)
                .toList();

        BigDecimal total = items.stream()
                .map(CartItemResponseDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponseDTO.builder()
                .id(cart.getId())
                .items(items)
                .total(total)
                .build();
    }
}
