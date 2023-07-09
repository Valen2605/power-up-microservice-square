package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.mappers;


import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.*;
import com.pragma.powerup.squaremicroservice.domain.model.*;
import java.util.List;


public interface IDishOrderEntityMapper {
    DishOrderEntity toEntity(DishOrder dishOrder);
    DishOrder toDishOrder(DishOrderEntity dishOrderEntity);

    List<DishOrder> toDishOrderList(List<DishOrderEntity> dishOrderEntities);

}
