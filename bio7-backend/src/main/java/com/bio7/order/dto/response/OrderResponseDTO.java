package com.bio7.order.dto.response;

import com.bio7.order.entity.OrderStatus;
import com.bio7.order.entity.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class OrderResponseDTO {

    private Long id;
    private OrderStatus status;
    private BigDecimal total;
    private List<OrderItemResponseDTO> items;
    private PaymentStatus paymentStatus;
}
