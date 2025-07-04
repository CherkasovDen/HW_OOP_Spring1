package org.skypro.skyshop.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public abstract class Product implements Searchable {
    private String nameProduct;
    private final UUID id;

    public Product(String nameProduct, UUID id) {
        if (nameProduct == null || nameProduct.isBlank()) {
            throw new IllegalArgumentException("Необходимо вести название продукта!!!");
        }
        this.nameProduct = nameProduct;
        this.id = id;

    }

    public UUID getId() {
        return id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public abstract int getPrice();

    @Override
    public abstract String toString();

    public abstract boolean isSpecial();

    @JsonIgnore
    @Override
    public String getSearchTerm() {
        return nameProduct;
    }

    @JsonIgnore
    @Override
    public String getContentType() {
        return "PRODUCT";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        return getNameProduct().equals(product.getNameProduct());
    }

    @Override
    public int hashCode() {
        return getNameProduct().hashCode();
    }

}
