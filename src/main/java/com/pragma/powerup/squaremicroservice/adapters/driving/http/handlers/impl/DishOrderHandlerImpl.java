package com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishOrderRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishOrdersRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response.DishOrderResponseDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.IDishOrderHandler;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper.IDishOrderRequestMapper;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper.IDishOrderResponseMapper;
import com.pragma.powerup.squaremicroservice.domain.api.IDishOrderServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DishOrderHandlerImpl implements IDishOrderHandler {

    private final IDishOrderServicePort dishOrderServicePort;
    private final IDishOrderRequestMapper dishOrderRequestMapper;
    private final IDishOrderResponseMapper dishOrderResponseMapper;

    @Override
    public void saveDishOrder(DishOrderRequestDto dishOrderRequestDto) {
        dishOrderServicePort.saveDishOrder(dishOrderRequestMapper.toDishOrder(dishOrderRequestDto));
    }

    @Override
    public void saveDishOrders(DishOrdersRequestDto dishOrdersRequestDto) {
        dishOrderServicePort.saveDishOrders(dishOrderRequestMapper.toDishesOrder(dishOrdersRequestDto.getDishOrders()));

    }

    @Override
    public List<DishOrderResponseDto> getOrders() {
        return dishOrderResponseMapper.toResponseList(dishOrderServicePort.getOrders());
    }

    @Override
    public List<DishOrderResponseDto> pendingOrders() {
        return dishOrderResponseMapper.toResponseList(dishOrderServicePort.pendingOrders());
    }
}
