package com.bio7.controller;

import com.bio7.model.Order;
import com.bio7.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(
            @RequestHeader("Authorization") String token,
            @RequestBody Order order) {

        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Unauthorized");
        }

        order.setOrderDate(java.time.LocalDateTime.now());
        return orderService.save(order);
    }
}
