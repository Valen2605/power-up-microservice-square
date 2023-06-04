package com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response.RestaurantsResponseDto;

import java.util.List;

public interface IRestaurantHandler {
    void saveRestaurant(RestaurantRequestDto restaurantRequestDto);

    List<RestaurantsResponseDto> getAllRestaurants(int page, int pageSize);

    void addEmployee(EmployeeRequestDto employeeRequestDto);


}
