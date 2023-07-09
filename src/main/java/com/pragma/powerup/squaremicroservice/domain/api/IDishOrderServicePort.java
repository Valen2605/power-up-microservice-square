package com.pragma.powerup.squaremicroservice.domain.api;

import com.pragma.powerup.squaremicroservice.domain.model.DishOrder;

import java.util.List;
import java.util.Queue;


public interface IDishOrderServicePort {
    void saveDishOrder(DishOrder dishOrder);

    void saveDishOrders(List<DishOrder> dishList);
    List<DishOrder> getOrders();

    void deleteDishOrder(Long id);

    List<DishOrder> pendingOrders();

}

