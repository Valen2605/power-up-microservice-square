package com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishUpdateRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.OrderUpdateRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response.OrderResponseDto;

import java.util.List;

public interface IOrderHandler {
    void saveOrder(Long idRestaurant);

    void assignOrder(Long id, OrderUpdateRequestDto orderUpdateRequestDto);

    List<OrderResponseDto> getOrders(String status, Long idRestaurant, int page, int pageSize);
}
