package com.pragma.powerup.squaremicroservice.domain.spi;

import com.pragma.powerup.squaremicroservice.domain.model.Order;
import com.pragma.powerup.squaremicroservice.domain.utility.StatusEnum;

import java.util.List;

public interface IOrderPersistencePort {
    void saveOrder(Order order);

    void assignOrder(Long id, Order order);

    void updateOrderReady(Long id, StatusEnum status);

    void updateOrderDelivered(Long id, StatusEnum status);

    List<Order> getOrders(String status, Long idRestaurant, int page, int pageSize);
}
