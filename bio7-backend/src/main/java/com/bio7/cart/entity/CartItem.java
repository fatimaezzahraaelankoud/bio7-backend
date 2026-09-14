package com.bio7.cart.entity;

import com.bio7.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="cart_items")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name ="cart_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_cart_items"))
    private Cart cart ;

   @ManyToOne(fetch = FetchType.LAZY , optional = false)
   @JoinColumn(name ="product_id",
           nullable = false,
           unique = true,
           foreignKey = @ForeignKey(name = "fk_product_items")
   )
   private Product product ;

    @Column(nullable = false)
    private Integer quantity;


}
