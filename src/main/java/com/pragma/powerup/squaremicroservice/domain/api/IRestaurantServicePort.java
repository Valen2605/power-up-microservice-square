package com.pragma.powerup.squaremicroservice.domain.api;


import com.pragma.powerup.squaremicroservice.domain.model.Employee;
import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantServicePort {
    void saveRestaurant(Restaurant restaurant);

    List<Restaurant> getAllRestaurants(int page, int pageSize);
    void addEmployee(Employee employee);
}
