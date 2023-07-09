package com.pragma.powerup.squaremicroservice.domain.spi;

import com.pragma.powerup.squaremicroservice.domain.model.DishOrder;

import java.util.List;


public interface IDishOrderPersistencePort {
    void saveDishOrder(DishOrder dishOrder);

    List<DishOrder> getOrders();

    List<DishOrder> pendingOrders();

}
