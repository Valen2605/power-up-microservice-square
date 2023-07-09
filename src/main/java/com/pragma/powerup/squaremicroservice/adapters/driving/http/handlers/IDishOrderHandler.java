package com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishOrderRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishOrdersRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response.DishOrderResponseDto;


import java.util.List;


public interface IDishOrderHandler {
    void saveDishOrder(DishOrderRequestDto dishOrderRequestDto);

    void saveDishOrders(DishOrdersRequestDto dishOrdersRequestDto);

    List<DishOrderResponseDto> getOrders();
    List<DishOrderResponseDto> pendingOrders();


}
