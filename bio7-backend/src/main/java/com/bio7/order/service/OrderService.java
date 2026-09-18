package com.bio7.order.service;

import com.bio7.auth.service.AuthenticatedUserService;
import com.bio7.cart.entity.Cart;
import com.bio7.cart.entity.CartItem;
import com.bio7.cart.repository.CartRepository;
import com.bio7.common.exception.ResourceNotFoundException;
import com.bio7.order.dto.response.OrderResponseDTO;
import com.bio7.order.entity.Order;
import com.bio7.order.entity.OrderItem;
import com.bio7.order.entity.OrderStatus;
import com.bio7.order.entity.PaymentStatus;
import com.bio7.order.mapper.OrderMapper;
import com.bio7.order.repository.OrderRepository;
import com.bio7.product.entity.Product;
import com.bio7.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;
    private final AuthenticatedUserService authenticatedUserService;

    @Transactional
    public OrderResponseDTO createOrder() {

        User user = authenticatedUserService.getCurrentUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Panier introuvable"
                        )
                );

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException(
                    "Le panier est vide"
            );
        }

        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDING)
                .total(BigDecimal.ZERO)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {

            Product product = cartItem.getProduct();

            if (!product.isActive()) {
                throw new IllegalArgumentException(
                        "Le produit " + product.getName()
                                + " n'est plus disponible"
                );
            }

            if (cartItem.getQuantity() > product.getStock()) {
                throw new IllegalArgumentException(
                        "Stock insuffisant pour le produit : "
                                + product.getName()
                );
            }

            BigDecimal unitPrice = product.getPrice();

            BigDecimal subtotal = unitPrice.multiply(
                    BigDecimal.valueOf(cartItem.getQuantity())
            );

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .productName(product.getName())
                    .unitPrice(unitPrice)
                    .quantity(cartItem.getQuantity())
                    .subtotal(subtotal)
                    .build();

            order.addItem(orderItem);

            total = total.add(subtotal);

            product.setStock(
                    product.getStock() - cartItem.getQuantity()
            );
        }

        order.setTotal(total);

        orderRepository.save(order);

        cart.getItems().clear();

        return orderMapper.toResponse(order);
    }


    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getMyOrders() {

        User user = authenticatedUserService.getCurrentUser();

        return orderRepository
                .findByUserIdOrderByIdDesc(user.getId())
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO getMyOrder(Long orderId) {

        User user = authenticatedUserService.getCurrentUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Commande introuvable avec l'id : "
                                        + orderId
                        )
                );

        if (!order.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException(
                    "Commande introuvable avec l'id : "
                            + orderId
            );
        }

        return orderMapper.toResponse(order);
    }


    @Transactional
    public OrderResponseDTO cancelOrder(Long orderId) {

        User user = authenticatedUserService.getCurrentUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Commande introuvable avec l'id : "
                                        + orderId
                        )
                );

        if (!order.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException(
                    "Commande introuvable avec l'id : "
                            + orderId
            );
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalArgumentException(
                    "Cette commande ne peut plus être annulée"
            );
        }

        for (OrderItem item : order.getItems()) {

            Product product = item.getProduct();

            product.setStock(
                    product.getStock() + item.getQuantity()
            );
        }

        order.setStatus(OrderStatus.CANCELLED);

        return orderMapper.toResponse(order);
    }

}
