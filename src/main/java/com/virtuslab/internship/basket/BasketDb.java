package com.virtuslab.internship.basket;

import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class BasketDb {

    public final Map<Long,Basket> basketMap;

    public BasketDb() {
        this.basketMap = new HashMap<>();
    }

    private Long getNextId() {
        if (basketMap.isEmpty())
            return 1L;
        else
            return Collections.max(basketMap.keySet()) + 1;
    }

    public Optional<Basket> findById(Long basketId) {
        return Optional.ofNullable(basketMap.get(basketId));
    }

    public Basket save(Basket basket) {
        if(basket.getId() == null){
            basket.setId(getNextId());
        }

        basketMap.put(basket.getId(),basket);

        return basket;
    }

    public void removeById(Long id){
        basketMap.remove(id);
    }
}
