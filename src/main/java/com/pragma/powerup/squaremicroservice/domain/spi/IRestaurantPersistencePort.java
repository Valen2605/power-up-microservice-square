package com.pragma.powerup.squaremicroservice.domain.spi;

import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;

import java.util.List;


public interface IRestaurantPersistencePort {
    void saveRestaurant(Restaurant restaurant);
    List<Restaurant> getAllRestaurants(int page, int pageSize);

    Restaurant findById(Long id);
}
