package com.bio7.order.controller;

import com.bio7.order.dto.response.OrderResponseDTO;
import com.bio7.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder() {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder());
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders() {

        return ResponseEntity.ok(
                orderService.getMyOrders()
        );
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getMyOrder(
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(
                orderService.getMyOrder(orderId)
        );
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(
                orderService.cancelOrder(orderId)
        );
    }
}
