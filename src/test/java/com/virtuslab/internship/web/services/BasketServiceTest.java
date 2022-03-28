package com.virtuslab.internship.web.services;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.basket.BasketDb;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.web.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Executable;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    BasketDb basketRepository;

    @InjectMocks
    BasketService basketService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testFindById() {
        //given
        var basket = new Basket();
        basket.setId(1L);
        var basketOptional = Optional.of(basket);

        when(basketRepository.findById(anyLong())).thenReturn(basketOptional);

        //when
        var returnedBasket = basketService.findById(1L);

        //then
        assertEquals(returnedBasket,basket);
        verify(basketRepository, times(1)).findById(anyLong());
    }

    @Test
    void testAddItemToBasket() {
        //given
        var basket = new Basket();
        basket.setId(1L);
        var basketOptional = Optional.of(basket);
        var productToAdd = new Product("test", Product.Type.DAIRY, BigDecimal.valueOf(3));

        when(basketRepository.findById(anyLong())).thenReturn(basketOptional);

        //when
        var returnedBasket = basketService.addItemToBasket(basket.getId(),productToAdd);

        //then
        assertTrue(returnedBasket.getProducts().contains(productToAdd));
        verify(basketRepository, times(1)).findById(anyLong());
    }

    @Test
    void testSaveBasket() {
        //given
        var basket = new Basket();
        basket.setId(1L);

        when(basketRepository.save(any(Basket.class))).thenReturn(basket);

        //when
        var returnedBasket = basketService.saveBasket(basket);

        //then
        assertEquals(returnedBasket, basket);
        verify(basketRepository, times(1)).save(any(Basket.class));
    }

    @Test
    void testRemoveItemFromBasket() {
        //given
        var basket = new Basket();
        basket.setId(1L);
        var basketOptional = Optional.of(basket);
        var productToBeRemoved = new Product("test", Product.Type.DAIRY, BigDecimal.valueOf(3));
        basket.getProducts().add(productToBeRemoved);

        when(basketRepository.findById(anyLong())).thenReturn(basketOptional);

        //when
        var returnedBasket = basketService.removeItemFromBasket(basket.getId(),productToBeRemoved);

        //then
        assertFalse(returnedBasket.getProducts().contains(productToBeRemoved));
        verify(basketRepository, times(1)).findById(anyLong());
    }

    @Test
    void testRemoveBasket() {
        //given
        var basket = new Basket();
        basket.setId(1L);
        var basketOptional = Optional.of(basket);

        when(basketRepository.findById(anyLong())).thenReturn(basketOptional);

        //when
        basketService.removeBasket(basket.getId());

        //then
        verify(basketRepository, times(1)).findById(anyLong());
        verify(basketRepository, times(1)).removeById(anyLong());
    }

    @Test
    void testFindByIdNotFoundException() throws Exception {
        //given
        when(basketRepository.findById(anyLong())).thenThrow(NotFoundException.class);

        //when

        //then
        assertThrows(NotFoundException.class,
                ()-> basketService.findById(1L));
    }
}