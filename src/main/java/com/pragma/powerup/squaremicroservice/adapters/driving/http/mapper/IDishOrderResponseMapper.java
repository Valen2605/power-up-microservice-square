package com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper;


import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response.DishOrderResponseDto;
import com.pragma.powerup.squaremicroservice.domain.model.DishOrder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishOrderResponseMapper {

    DishOrderResponseDto toResponse(DishOrder dishOrder);
    List<DishOrderResponseDto> toResponseList(List<DishOrder> dishOrderList);

}
