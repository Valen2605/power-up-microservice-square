package com.pragma.powerup.squaremicroservice.domain.api;


import com.pragma.powerup.squaremicroservice.domain.model.Order;

import java.util.List;

public interface IOrderServicePort {
    void saveOrder(Long idRestaurant);

    List<Order> getOrders(String status, Long idRestaurant, int page, int pageSize);
}

