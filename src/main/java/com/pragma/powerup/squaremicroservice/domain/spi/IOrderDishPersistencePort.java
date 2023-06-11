package com.pragma.powerup.squaremicroservice.domain.spi;


import com.pragma.powerup.squaremicroservice.domain.model.OrderDish;

public interface IOrderDishPersistencePort {
    void saveOrderDish(OrderDish orderDish);
}
