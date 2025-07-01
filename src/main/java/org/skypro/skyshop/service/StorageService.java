package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageService {
    private final Map<UUID, Product> productsMap;
    private final Map<UUID, Article> articlesMap;


    public StorageService() {
        this.productsMap = new HashMap<>();
        this.articlesMap = new HashMap<>();
        initializeTestData();

    }

    public Collection<Product> getAllProducts() {
        return productsMap.values();
    }

    public Collection<Article> getAllArticles() {
        return articlesMap.values();
    }

    public Collection<Searchable> getAllSearchables() {
        List<Searchable> searchables = new ArrayList<>();
        searchables.addAll(productsMap.values());
        searchables.addAll(articlesMap.values());
        return searchables;
    }


    private void initializeTestData() {

        Product apple = new SimpleProduct("Яблоко Гала", 150, UUID.randomUUID());
        Product apple1 = new SimpleProduct("Яблоко Голден", 250, UUID.randomUUID());
        Product apple2 = new SimpleProduct("Яблоко Спартан", 350, UUID.randomUUID());
        Product apple3 = new SimpleProduct("Яблоко Фуджи", 450, UUID.randomUUID());
        Product apple4 = new SimpleProduct("Яблоко Глостер", 550, UUID.randomUUID());
        Product apple5 = new SimpleProduct("Яблоко Ренет", 650, UUID.randomUUID());
        Product milk = new SimpleProduct("Молоко", 82, UUID.randomUUID());
        Product bread = new DiscountedProduct("Хлеб", 70, 30, UUID.randomUUID());
        Product bananas = new SimpleProduct("Бананы", 150, UUID.randomUUID());
        Product potato = new FixPriceProduct("Картофель", UUID.randomUUID());
        Product meat = new SimpleProduct("Мясо", 400, UUID.randomUUID());

        Article article1 = new Article("телефон", "инструкция к телефону", UUID.randomUUID());
        Article article2 = new Article("нивелир", "руководство пользователя", UUID.randomUUID());
        Article article3 = new Article("перфоратор", "руководство по эксплуатации", UUID.randomUUID());
        Article article11 = new Article("Яблоко1", "приготовление джема", UUID.randomUUID());
        Article article12 = new Article("Яблоко123", "приготовление варенья", UUID.randomUUID());

        addProduct(apple);
        addProduct(apple1);
        addProduct(apple2);
        addProduct(apple3);
        addProduct(apple4);
        addProduct(apple5);
        addProduct(milk);
        addProduct(bread);
        addProduct(bananas);
        addProduct(potato);
        addProduct(meat);

        addArticle(article1);
        addArticle(article2);
        addArticle(article3);
        addArticle(article11);
        addArticle(article12);
    }

    private void addProduct(Product product) {
        productsMap.put(product.getId(), product);
    }

    private void addArticle(Article article) {
        articlesMap.put(article.getId(), article);
    }

}

