package com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.impl;



import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.OrderUpdateRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper.IOrderRequestMapper;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper.IOrderResponseMapper;
import com.pragma.powerup.squaremicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.squaremicroservice.domain.utility.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderHandlerImpl implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final IOrderResponseMapper orderResponseMapper;
    private final IOrderRequestMapper orderRequestMapper;
    @Override
    public void saveOrder(Long idRestaurant) {
        orderServicePort.saveOrder(idRestaurant);
    }

    @Override
    public void assignOrder(Long id, OrderUpdateRequestDto orderUpdateRequestDto) {
        orderServicePort.assignOrder(id,orderRequestMapper.toOrder(orderUpdateRequestDto));
    }

    @Override
    public List<OrderResponseDto> getOrders(String status, Long idRestaurant, int page, int pageSize) {
        return orderResponseMapper.toResponseList(orderServicePort.getOrders(status,idRestaurant,page, pageSize));
    }

    @Override
    public void updateOrderReady(Long id, StatusEnum status) {
        orderServicePort.updateOrderReady(id,status);
    }

    @Override
    public void updateOrderDelivered(Long id, StatusEnum status, String codeOrder) {
        orderServicePort.updateOrderDelivered(id,status,codeOrder);
    }

    @Override
    public void updateOrderCanceled(Long id, StatusEnum status) {
        orderServicePort.updateOrderCanceled(id,status);
    }

}
