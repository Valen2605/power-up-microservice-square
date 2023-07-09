package com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper;


import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishOrderRequestDto;
import com.pragma.powerup.squaremicroservice.domain.model.*;


import java.util.List;

public interface IDishOrderRequestMapper {

    DishOrder toDishOrder(DishOrderRequestDto dishOrderRequestDto);
    List<DishOrder> toDishesOrder(List<DishOrderRequestDto> dishOrderRequestDto);
}
