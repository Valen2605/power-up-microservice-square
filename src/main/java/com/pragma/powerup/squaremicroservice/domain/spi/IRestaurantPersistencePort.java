package com.pragma.powerup.squaremicroservice.domain.spi;

import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;


public interface IRestaurantPersistencePort {
    void saveRestaurant(Restaurant restaurant);
}
