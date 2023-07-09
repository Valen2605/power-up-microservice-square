package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.DishOrderEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.mappers.IDishOrderEntityMapper;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IDishOrderRepository;
import com.pragma.powerup.squaremicroservice.domain.model.DishOrder;
import com.pragma.powerup.squaremicroservice.domain.spi.IDishOrderPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Transactional
public class DishOrderMysqlAdapter implements IDishOrderPersistencePort {

    private final IDishOrderRepository dishOrderRepository;
    private final IDishOrderEntityMapper dishOrderEntityMapper;

    @Override
    public void saveDishOrder(DishOrder dishOrder) {

       dishOrderRepository.save(dishOrderEntityMapper.toEntity(dishOrder));

    }

    @Override
    public List<DishOrder> getOrders() {
        List<DishOrderEntity> dishOrderEntities = dishOrderRepository.findAll();
        return dishOrderEntityMapper.toDishOrderList(dishOrderEntities);

    }

    @Override
    public List<DishOrder> pendingOrders() {
        List<DishOrderEntity> dishOrderEntities = dishOrderRepository.findAll();
        return dishOrderEntityMapper.toDishOrderList(dishOrderEntities);
    }

}

