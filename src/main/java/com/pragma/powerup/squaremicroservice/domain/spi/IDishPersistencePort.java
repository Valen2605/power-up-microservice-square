package com.pragma.powerup.squaremicroservice.domain.spi;

import com.pragma.powerup.squaremicroservice.domain.model.Dish;

public interface IDishPersistencePort {
    void saveDish(Dish dish);
}
