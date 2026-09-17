package com.bio7.cart.service;


import com.bio7.auth.service.AuthenticatedUserService;
import com.bio7.cart.dto.request.AddToCartRequestDTO;
import com.bio7.cart.dto.request.UpdateCartItemRequestDTO;
import com.bio7.cart.dto.response.CartResponseDTO;
import com.bio7.cart.entity.Cart;
import com.bio7.cart.entity.CartItem;
import com.bio7.cart.mapper.CartMapper;
import com.bio7.cart.repository.CartItemRepository;
import com.bio7.cart.repository.CartRepository;
import com.bio7.common.exception.ResourceNotFoundException;
import com.bio7.product.entity.Product;
import com.bio7.product.repository.ProductRepository;
import com.bio7.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository ;
    private final CartItemRepository cartItemRepository ;
    private final CartMapper cartMapper ;
    private final ProductRepository productRepository ;
    private final AuthenticatedUserService authenticatedUserService;
    @Transactional
    public CartResponseDTO addItem(AddToCartRequestDTO request) {

        User user = authenticatedUserService.getCurrentUser();

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Produit introuvable avec l'id : "
                                        + request.getProductId()
                        )
                );

        if (!product.isActive()) {
            throw new IllegalArgumentException(
                    "Ce produit n'est plus disponible"
            );
        }

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() ->
                        cartRepository.save(
                                Cart.builder()
                                        .user(user)
                                        .build()
                        )
                );

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(
                        cart.getId(),
                        product.getId()
                )
                .orElse(null);

        int newQuantity;

        if (cartItem != null) {

            newQuantity =
                    cartItem.getQuantity() + request.getQuantity();

        } else {

            newQuantity = request.getQuantity();

            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(newQuantity)
                    .build();
        }

        if (newQuantity > product.getStock()) {
            throw new IllegalArgumentException(
                    "Stock insuffisant. Stock disponible : "
                            + product.getStock()
            );
        }

        cartItem.setQuantity(newQuantity);

        cartItemRepository.save(cartItem);

        return cartMapper.toResponse(cart);
    }

    @Transactional(readOnly = true)
    public CartResponseDTO getCart() {

        User user = authenticatedUserService.getCurrentUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() ->
                        cartRepository.save(
                                Cart.builder()
                                        .user(user)
                                        .build()
                        )
                );

        return cartMapper.toResponse(cart);
    }

    @Transactional
    public CartResponseDTO updateItem(
            Long productId,
            UpdateCartItemRequestDTO request
    ) {
        User user = authenticatedUserService.getCurrentUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Panier introuvable"
                        )
                );

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(
                        cart.getId(),
                        productId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Produit absent du panier"
                        )
                );

        Product product = cartItem.getProduct();

        if (!product.isActive()) {
            throw new IllegalArgumentException(
                    "Ce produit n'est plus disponible"
            );
        }

        if (request.getQuantity() > product.getStock()) {
            throw new IllegalArgumentException(
                    "Stock insuffisant. Stock disponible : "
                            + product.getStock()
            );
        }

        cartItem.setQuantity(request.getQuantity());

        cartItemRepository.save(cartItem);

        return cartMapper.toResponse(cart);
    }



    @Transactional
    public CartResponseDTO removeItem(Long productId) {

        User user = authenticatedUserService.getCurrentUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Panier introuvable"
                        )
                );

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(
                        cart.getId(),
                        productId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Produit absent du panier"
                        )
                );

        cart.removeItem(cartItem);

        cartItemRepository.delete(cartItem);

        return cartMapper.toResponse(cart);
    }


}
