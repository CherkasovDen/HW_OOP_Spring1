package org.skypro.skyshop.model.product;

import java.util.UUID;

public class DiscountedProduct extends Product {
    private int basePrice;
    private int discount;

    public DiscountedProduct(String nameProduct, int basePrice, int discount, UUID id) {
        super(nameProduct, id);
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Цена продукта должна быть больше 0!!!");
        }
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Процент скидки должен быть в диапазоне от 0 до 100 включительно");
        }
        this.basePrice = basePrice;
        this.discount = discount;
    }

    @Override
    public int getPrice() {
        return basePrice - (basePrice * discount / 100);
    }


    @Override
    public String toString() {
        return getNameProduct() + ": " + getPrice() + " (с учетом скидки " + discount + " %)";
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String getName() {
        return getNameProduct();
    }
}
