package com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response.RestaurantsResponseDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.IRestaurantHandler;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper.IRestaurantResponseMapper;
import com.pragma.powerup.squaremicroservice.domain.api.IRestaurantServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantHandlerImpl implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantResponseMapper restaurantResponseMapper;

    @Override
    public void saveRestaurant(RestaurantRequestDto restaurantRequestDto){
        restaurantServicePort.saveRestaurant(restaurantRequestMapper.toRestaurant(restaurantRequestDto));
    }

    @Override
    public List<RestaurantsResponseDto> getAllRestaurants(int page, int pageSize) {
        return restaurantResponseMapper.toResponseList(restaurantServicePort.getAllRestaurants(page, pageSize));
    }
}
