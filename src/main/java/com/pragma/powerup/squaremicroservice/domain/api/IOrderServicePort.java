package com.pragma.powerup.squaremicroservice.domain.api;


import com.pragma.powerup.squaremicroservice.domain.model.Order;

public interface IOrderServicePort {
    void saveOrder(Long idRestaurant);
}

