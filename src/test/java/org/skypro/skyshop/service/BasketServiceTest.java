package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @Mock
    private ProductBasket basket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;


    @Test
    public void testAddNonExistingProduct_ThrowsException() {

        UUID productId = UUID.randomUUID();
        //Given
        when(storageService.getProductById(productId)).thenReturn(Optional.empty());
        //When&Then
        assertThrows(NoSuchProductException.class, () -> {
            basketService.addProduct(productId);
        });
        verify(basket, never()).addProduct(any());
    }


    @Test
    void testAddProduct_ExistingProduct_CallsAddProduct() {
        //Given
        UUID productId = UUID.randomUUID();
        Product product = new SimpleProduct("Test Product", 100, productId);
        when(storageService.getProductById(productId)).thenReturn(Optional.of(product));
        //When
        basketService.addProduct(productId);
        //Then
        verify(basket).addProduct(productId);
    }


    @Test
    void getUserBasket_EmptyBasket_ReturnsEmptyBasket() {
        //Given
        when(basket.getProductsBasket()).thenReturn(Collections.emptyMap());
        //When
        UserBasket userBasket = basketService.getUserBasket();
        //Then
        assertNotNull(userBasket);
        assertTrue(userBasket.getItems().isEmpty());
        verify(basket).getProductsBasket();

    }


    @Test
    void getUserBasket_WithItems_ReturnsCorrectUserBasket() {
        //Given
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();

        Map<UUID, Integer> basketMap = new HashMap<>();
        basketMap.put(productId1, 2);
        basketMap.put(productId2, 3);

        when(basket.getProductsBasket()).thenReturn(basketMap);

        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);

        when(storageService.getProductById(productId1)).thenReturn(Optional.of(product1));
        when(storageService.getProductById(productId2)).thenReturn(Optional.of(product2));

        //When
        UserBasket userBasket = basketService.getUserBasket();
        //Then
        assertNotNull(userBasket);
        List<BasketItem> items = userBasket.getItems();
        assertEquals(2, items.size());

               // Проверка содержимого items
        boolean foundProduct1 = false;
        boolean foundProduct2 = false;

        for (BasketItem item : items) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();

            if (product == product1) {
                foundProduct1 = true;
                assertEquals(2, quantity);
            } else if (product == product2) {
                foundProduct2 = true;
                assertEquals(3, quantity);
            }
        }

        assertTrue(foundProduct1, "В корзине товар1 отсутствует ");
        assertTrue(foundProduct2, "В корзине товар2 отсутствует ");


        verify(basket).getProductsBasket();
        verify(storageService).getProductById(productId1);
        verify(storageService).getProductById(productId2);
    }


}

