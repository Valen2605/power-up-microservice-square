package com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response.OrderResponseDto;

import java.util.List;

public interface IOrderHandler {
    void saveOrder(Long idRestaurant);

    List<OrderResponseDto> getOrders(String status, Long idRestaurant, int page, int pageSize);
}
