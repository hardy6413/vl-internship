package com.virtuslab.internship.web.services;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.basket.BasketDb;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.web.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BasketService {

    BasketDb basketRepository;
    ProductService productService;

    public BasketService(BasketDb basketRepository, ProductService productService) {
        this.basketRepository = basketRepository;
        this.productService = productService;
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
        Product savedProduct = productService.findByName(product.name());

        basket.getProducts().add(savedProduct);

        return basket;
    }

    public Basket saveBasket(Basket basket){
        return basketRepository.save(basket);
    }

    public Basket removeItemFromBasket(Long id, String productName) {
        Basket basket = this.findById(id);
        Product productToRemove = productService.findByName(productName);

        basket.getProducts().remove(productToRemove);
        return  basket;
    }

    public void removeBasket(Long id) {
        Basket basket = this.findById(id);

        basketRepository.removeById(id);
    }
}
