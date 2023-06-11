package com.pragma.powerup.squaremicroservice.domain.api;


import com.pragma.powerup.squaremicroservice.domain.model.OrderDish;

public interface IOrderDishServicePort {
    void saveOrderDish(OrderDish orderDish);
}

