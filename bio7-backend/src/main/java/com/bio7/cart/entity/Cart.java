package com.bio7.cart.entity;

import com.bio7.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="carts")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @OneToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn( name = "user_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_cart_user"))
    private User user ;

    @OneToMany(mappedBy = "cart")
    @Builder.Default
    private List<CartItem> items = new ArrayList<>();

    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
    }


}
