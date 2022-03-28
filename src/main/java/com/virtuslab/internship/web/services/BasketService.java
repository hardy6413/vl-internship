package com.virtuslab.internship.web.services;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.basket.BasketDb;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.web.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BasketService {

    BasketDb basketRepository;

    @Autowired
    public BasketService(BasketDb basketRepository) {
        this.basketRepository = basketRepository;
    }

    public Basket findById(Long basketId){
        Optional<Basket> optionalBasket = basketRepository.findById(basketId);

        if (optionalBasket.isEmpty()){
            throw new NotFoundException("Basket not found for ID: " +basketId);
        }

        return optionalBasket.get();
    }

    public Basket addItemToBasket(Long basketId, Product product){
        Basket basket = this.findById(basketId);

        basket.getProducts().add(product);

        return basket;
    }

    public Basket saveBasket(Basket basket){
        return basketRepository.save(basket);
    }

    public Basket removeItemFromBasket(Long id, Product product) {
        Basket basket = this.findById(id);

        basket.getProducts().remove(product);

        return  basket;
    }

    public void removeBasket(Long id) {
        Basket basket = this.findById(id);

        basketRepository.removeById(id);
    }
}
