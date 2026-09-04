package com.bio7.product.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String name ;
    private String description ;
    private Double price ;
    private String imageUrl;
    private String category;
    private String badge; // "Best-seller", "Nouveau", "Premium"
    private int stock ;
}
