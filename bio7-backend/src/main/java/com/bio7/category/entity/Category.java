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

    @Size(max=50)
    private String name ;

    @Size(max=150)
    private String description ;

    @OneToMany(fetch =FetchType.LAZY)
    private List<Product> product = new ArrayList<>() ;
}
