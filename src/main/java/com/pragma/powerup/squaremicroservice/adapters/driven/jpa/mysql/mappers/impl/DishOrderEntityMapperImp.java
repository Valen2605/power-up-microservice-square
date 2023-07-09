package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.mappers.impl;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.DishOrderEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.mappers.IDishOrderEntityMapper;
import com.pragma.powerup.squaremicroservice.domain.model.DishOrder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class DishOrderEntityMapperImp implements IDishOrderEntityMapper {


    @Override
    public DishOrderEntity toEntity(DishOrder dishOrder) {
        if (dishOrder == null) {
            return null;
        }

        DishOrderEntity dishOrderEntity = new DishOrderEntity();

        dishOrderEntity.setId(dishOrder.getId());
        dishOrderEntity.setTypeDish(dishOrder.getTypeDish());
        dishOrderEntity.setPriority(dishOrder.getPriority());
        dishOrderEntity.setCompanion(dishOrder.getCompanion());
        dishOrderEntity.setGrams(dishOrder.getGrams());
        dishOrderEntity.setTypeDessert(dishOrder.getTypeDessert());
        dishOrderEntity.setComplement(dishOrder.getComplement());
        dishOrderEntity.setFlavor(dishOrder.getFlavor());

        return dishOrderEntity;
    }

    @Override
    public DishOrder toDishOrder(DishOrderEntity dishOrderEntity) {
        if ( dishOrderEntity == null ) {
            return null;
        }

        DishOrder dishOrder = new DishOrder();

        dishOrder.setId(dishOrderEntity.getId());
        dishOrder.setTypeDish(dishOrderEntity.getTypeDish());
        dishOrder.setPriority(dishOrderEntity.getPriority());
        dishOrder.setCompanion(dishOrderEntity.getCompanion());
        dishOrder.setGrams(dishOrderEntity.getGrams());
        dishOrder.setTypeDessert(dishOrderEntity.getTypeDessert());
        dishOrder.setComplement(dishOrderEntity.getComplement());
        dishOrder.setFlavor(dishOrderEntity.getFlavor());

        return dishOrder;
    }

    protected DishOrder dishOrderEntityToDishOrder(DishOrderEntity dishOrderEntity) {
        if ( dishOrderEntity == null ) {
            return null;
        }

        DishOrder dishOrder = new DishOrder();

        dishOrder.setId(dishOrderEntity.getId());
        dishOrder.setTypeDish(dishOrderEntity.getTypeDish());
        dishOrder.setPriority(dishOrderEntity.getPriority());



        return dishOrder;
    }

    @Override
    public List<DishOrder> toDishOrderList(List<DishOrderEntity> dishOrderEntities) {
        if ( dishOrderEntities == null ) {
            return null;
        }

        List<DishOrder> list = new ArrayList<DishOrder>( dishOrderEntities.size() );
        for ( DishOrderEntity dishOrderEntity : dishOrderEntities ) {
            list.add( toDishOrder( dishOrderEntity ) );
        }

        return list;
    }
}
