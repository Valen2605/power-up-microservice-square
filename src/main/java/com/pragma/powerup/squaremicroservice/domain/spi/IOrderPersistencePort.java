package com.pragma.powerup.squaremicroservice.domain.spi;

import com.pragma.powerup.squaremicroservice.domain.model.Order;

public interface IOrderPersistencePort {
    void saveOrder(Order order);
}
