package com.bio7.order.mapper;

import com.bio7.order.dto.response.OrderItemResponseDTO;
import com.bio7.order.dto.response.OrderResponseDTO;
import com.bio7.order.entity.Order;
import com.bio7.order.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderItemResponseDTO toItemResponse(OrderItem item) {
        return OrderItemResponseDTO.builder()
                .productId(item.getProduct().getId())
                .productName(item.getProductName())
                .unitPrice(item.getUnitPrice())
                .quantity(item.getQuantity())
                .subtotal(item.getSubtotal())
                .build();
    }

    public OrderResponseDTO toResponse(Order order) {

        List<OrderItemResponseDTO> items = order.getItems()
                .stream()
                .map(this::toItemResponse)
                .toList();

        return OrderResponseDTO.builder()
                .id(order.getId())
                .status(order.getStatus())
                .total(order.getTotal())
                .items(items)
                .paymentStatus(order.getPaymentStatus())
                .build();
    }
}
