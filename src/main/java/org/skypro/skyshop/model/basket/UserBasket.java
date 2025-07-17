package org.skypro.skyshop.model.basket;

import java.util.List;

public class UserBasket {
    private final List<BasketItem> items;
    private final int totalPrice;

    public UserBasket(List<BasketItem> items) {
        this.items = items;
        this.totalPrice = items.stream().mapToInt(i -> i.getProduct().getPrice() * i.getQuantity()).sum();
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

}
