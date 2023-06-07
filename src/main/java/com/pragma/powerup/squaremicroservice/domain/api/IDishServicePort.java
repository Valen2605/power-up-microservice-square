package com.pragma.powerup.squaremicroservice.domain.api;

import com.pragma.powerup.squaremicroservice.domain.model.Dish;
import java.util.List;


public interface IDishServicePort {
    void saveDish(Dish dish);
    void updateDish(Long id, Dish dish);
    void enableDisableDish(Long id);
    List<Dish> getDishes(Long idRestaurant,Long IdCategory,int page, int pageSize);
}

