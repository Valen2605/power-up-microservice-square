package com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.impl;


import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper.IOrderResponseMapper;
import com.pragma.powerup.squaremicroservice.domain.api.IOrderServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderHandlerImpl implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final IOrderResponseMapper orderResponseMapper;
    @Override
    public void saveOrder(Long idRestaurant) {
        orderServicePort.saveOrder(idRestaurant);
    }

    @Override
    public List<OrderResponseDto> getOrders(String status, Long idRestaurant, int page, int pageSize) {
        return orderResponseMapper.toResponseList(orderServicePort.getOrders(status,idRestaurant,page, pageSize));
    }
}
