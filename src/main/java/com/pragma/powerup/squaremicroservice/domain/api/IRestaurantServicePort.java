package com.pragma.powerup.squaremicroservice.domain.api;


import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;

public interface IRestaurantServicePort {
    void saveRestaurant(Restaurant restaurant);
}
