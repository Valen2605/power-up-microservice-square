package com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.impl;


import com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.squaremicroservice.domain.api.IOrderServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;




@Service
@RequiredArgsConstructor
public class OrderHandlerImpl implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    @Override
    public void saveOrder(Long idRestaurant) {
        orderServicePort.saveOrder(idRestaurant);
    }
}
