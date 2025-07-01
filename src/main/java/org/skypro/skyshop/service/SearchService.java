package org.skypro.skyshop.service;

import org.skypro.skyshop.model.search.SearchResult;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final StorageService storageService;

    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }

    public Collection<SearchResult> search(String SearchTerm) {
        return storageService.getAllSearchables().stream()
                .filter(searchable -> searchable.getSearchTerm().contains(SearchTerm))
                .map(SearchResult::fromSearchable)
                .collect(Collectors.toList());
    }

}
