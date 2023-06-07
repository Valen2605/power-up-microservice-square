package com.pragma.powerup.squaremicroservice.domain.spi;

import com.pragma.powerup.squaremicroservice.domain.model.Dish;

import java.util.List;

public interface IDishPersistencePort {
    void saveDish(Dish dish);

    void updateDish(Long id, Dish dish);

    void enableDisableDish(Long id);

    List<Dish> getDishes(Long idRestaurant, Long idCategory, int page, int pageSize);
}
