package com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers;


import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.OrderDishRequestDto;

public interface IOrderDishHandler {
    void saveOrderDish(OrderDishRequestDto orderDishRequestDto);
}
