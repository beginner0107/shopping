package com.group.shopping.domain.cart;

import com.group.shopping.domain.BaseEntity;
import com.group.shopping.domain.item.Item;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "cart_item")
public class CartItem extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;

    public static CartItem createCartItem(Cart cart, Item item, int count) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(count);
        return cartItem;
    }

    public void addCount(int count) {
        this.count += count;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void updateCount(int count) {
        this.count = count;
    }
}
