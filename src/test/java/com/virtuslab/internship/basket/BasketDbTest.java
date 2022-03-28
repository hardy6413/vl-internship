package com.virtuslab.internship.basket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasketDbTest {

    BasketDb basketRepository;

    @BeforeEach
    void setUp() {
        basketRepository = new BasketDb();
        var firstBasket = new Basket();
        firstBasket.setId(1L);
        var secondBasket = new Basket();
        secondBasket.setId(2L);

        basketRepository.save(firstBasket);
        basketRepository.save(secondBasket);
    }

    @Test
    void findById() {
        //given

        //when
        var basketOptional = basketRepository.findById(2L);
        var basket = basketOptional.get();
        //then
        assertNotNull(basket);
    }

    @Test
    void save() {
        //given
        var basket = new Basket();
        basket.setId(3L);

        //when
        basketRepository.save(basket);

        //then
        assertNotNull(basketRepository.findById(3L));
    }

    @Test
    void removeById() {
        //given

        //when
        basketRepository.removeById(2L);

        //then
        assertFalse(basketRepository.basketMap.containsKey(2L));
    }
}