package com.pragma.powerup.squaremicroservice.domain.api;

import com.pragma.powerup.squaremicroservice.domain.model.Dish;
import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;

import java.util.List;


public interface IDishServicePort {
    void saveDish(Dish dish);
    void updateDish(Long id, Dish dish);
    void enableDisableDish(Long id);
    List<Dish> getDishes(int page, int pageSize);
}

