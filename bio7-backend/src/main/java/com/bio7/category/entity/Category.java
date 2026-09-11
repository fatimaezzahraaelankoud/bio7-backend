package com.bio7.category.entity;

import com.bio7.product.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="categories")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false,length = 50 )
    private String name ;

    @Column(nullable = false,length = 150 )
    private String description ;

    @Column(nullable = false )
    private boolean active ;

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private List<Product> product = new ArrayList<>() ;
}
