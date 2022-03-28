package com.virtuslab.internship.web.controllers;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.web.services.BasketService;
import com.virtuslab.internship.web.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/basket")
public class BasketController {

    private final BasketService basketService;
    private final ProductService productService;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Basket> getBasket(@PathVariable Long id) {
        Basket basket = basketService.findById(id);

        return new ResponseEntity<>(basket, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/add")
    public ResponseEntity<Basket> addProductToBasket(@PathVariable Long id, @RequestBody Product product){
        Product productToBeAdded = productService.findByName(product.name());
        Basket basket = basketService.addItemToBasket(id, productToBeAdded);

        return new ResponseEntity<>(basket, HttpStatus.OK);
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Basket> createBasket() {
        Basket basket = new Basket();

        Basket createdBasket = basketService.saveBasket(basket);

        return new ResponseEntity<>(createdBasket, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}/{productName}")
    @ResponseBody
    public ResponseEntity<Basket> deleteItemFromBasket(@PathVariable Long id, @PathVariable String productName){
        Product productToBeDeleted = productService.findByName(productName);
        Basket basket = basketService.removeItemFromBasket(id,productToBeDeleted);

        return new ResponseEntity<>(basket,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBasket(@PathVariable Long id){
        basketService.removeBasket(id);

        return new ResponseEntity<>("Deleted basket ID:"+id,HttpStatus.OK);
    }


}
