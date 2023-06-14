package com.pragma.powerup.squaremicroservice.domain.spi;

import com.pragma.powerup.squaremicroservice.domain.model.Dish;
import com.pragma.powerup.squaremicroservice.domain.model.Order;

import java.util.List;

public interface IOrderPersistencePort {
    void saveOrder(Order order);

    void assignOrder(Long id, Order order);

    List<Order> getOrders(String status, Long idRestaurant, int page, int pageSize);
}
