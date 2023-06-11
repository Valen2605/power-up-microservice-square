package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.adapter;


import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.mappers.IOrderEntityMapper;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.squaremicroservice.domain.model.Order;
import com.pragma.powerup.squaremicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.utility.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public class OrderMysqlAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;

    @Override
    public void saveOrder(Order order) {
        Optional<OrderEntity> orderEntity = orderRepository.findClientById(order.getIdClient());
        List<OrderEntity> orderStatus = orderRepository.findClientByIdAndStatus(
                order.getIdClient(),StatusEnum.ENTREGADO.toString());


        orderRepository.save(orderEntityMapper.toEntity(order));

    }
}

