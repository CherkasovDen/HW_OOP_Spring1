package org.skypro.skyshop.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {


    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    @Test
    public void testSearch_NoObjectsInStorage() {
        //Given
        when(storageService.getAllSearchables()).thenReturn(Collections.emptyList());
        //When
        var results = searchService.search("яблоко");
        //Then
        assertTrue(results.isEmpty());
        verify(storageService, times(1)).getAllSearchables();
    }


    @Test
    void testSearch_NoMatchingObjects() {
        //Given
        Searchable product1 = new SimpleProduct("Молоко", 82, UUID.randomUUID());
        Searchable product2 = new SimpleProduct("Хлеб", 70, UUID.randomUUID());
        Searchable article = new Article("Телефон", "Инструкция", UUID.randomUUID());

        when(storageService.getAllSearchables()).thenReturn(Arrays.asList(product1, product2, article));

        //When
        var results = searchService.search("Яблоко");

        // Then
        assertTrue(results.isEmpty());
        verify(storageService, times(1)).getAllSearchables();
    }


    @Test
    public void testSearch_WithMatchingObjects() {
        //Given
        Searchable product1 = new SimpleProduct("Яблоко Гала", 150, UUID.randomUUID());
        Searchable article1 = new Article("Яблоки", "Рецепты", UUID.randomUUID());

        when(storageService.getAllSearchables())
                .thenReturn(Arrays.asList(product1, article1));

        try (var mockedStatic = Mockito.mockStatic(SearchResult.class)) {
            SearchResult mockResult = mock(SearchResult.class);
            mockedStatic.when(() -> SearchResult.fromSearchable(product1)).thenReturn(mockResult);
            //When
            var results = searchService.search("Яблоко");
            // Then
            assertEquals(1, results.size(), "Должен быть найден один подходящий объект");
            assertTrue(results.contains(mockResult), "Результат должен содержать ожидаемый объект");
            mockedStatic.verify(() -> SearchResult.fromSearchable(product1));
            verify(storageService, times(1)).getAllSearchables();
        }
    }

}
