package com.pragma.powerup.squaremicroservice.domain.api;

import com.pragma.powerup.squaremicroservice.domain.model.Order;
import com.pragma.powerup.squaremicroservice.domain.utility.StatusEnum;

import java.util.List;

public interface IOrderServicePort {
    void saveOrder(Long idRestaurant);

    void assignOrder(Long id, Order order);

    void updateOrderReady(Long id, StatusEnum status);

    void updateOrderDelivered(Long id, StatusEnum status, String codeOrder);

    List<Order> getOrders(String status, Long idRestaurant, int page, int pageSize);

    void updateOrderCanceled(Long id, StatusEnum status);

}

