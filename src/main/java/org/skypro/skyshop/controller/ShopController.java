package org.skypro.skyshop.controller;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.service.StorageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class ShopController {

    private final StorageService storageService;

    public ShopController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/products")
    public Collection<Product> getAllProducts() {
        return storageService.getAllProducts();
    }

    @GetMapping("/articles")
    public Collection<Article> getAllArticles() {
        return storageService.getAllArticles();
    }

    @GetMapping("/search")
    public List<SearchResult> search(@RequestParam("pattern") String pattern) {
        return storageService.getAllSearchables().stream()
                .filter(searchable -> searchable.getSearchTerm().contains(pattern))
                .map(SearchResult::fromSearchable)
                .toList();
    }


}
