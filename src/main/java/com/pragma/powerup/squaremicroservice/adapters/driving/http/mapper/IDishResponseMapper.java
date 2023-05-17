package com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper;


import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.pragma.powerup.squaremicroservice.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishResponseMapper {

    @Mapping(target = "idCategory", source = "idCategory")
    @Mapping(target = "idRestaurant", source = "idRestaurant")
    DishResponseDto toResponse(Dish dish);
    List<DishResponseDto> toResponseList(List<Dish> dishList);
}
