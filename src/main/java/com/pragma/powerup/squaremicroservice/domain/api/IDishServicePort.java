package com.pragma.powerup.squaremicroservice.domain.api;

import com.pragma.powerup.squaremicroservice.domain.model.Dish;
public interface IDishServicePort {
    void saveDish(Dish dish);
    void updateDish(Long id, Dish dish);
}

