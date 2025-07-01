package org.skypro.skyshop.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.UUID;

public class Article implements Searchable {
    private String title;
    private String text;
    private final UUID id;

    public Article(String title, String text, UUID id) {
        this.title = title;
        this.text = text;
        this.id = id;

    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return title + "\n" + text;
    }

    @JsonIgnore
    @Override
    public String getSearchTerm() {
        return title + " " + text;
    }

    @JsonIgnore
    @Override
    public String getContentType() {
        return "ARTICLE";
    }

    public String getName() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return title.equals(article.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
