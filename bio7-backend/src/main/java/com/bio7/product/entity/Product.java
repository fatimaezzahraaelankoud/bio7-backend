package com.bio7.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, length = 250)
    private String imageUrl;

    @Column(nullable = false, length = 50)
    private String category;


    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private Badge badge;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private boolean active = true;
}
