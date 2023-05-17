package com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper;


import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.squaremicroservice.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishRequestMapper {
    @Mapping(target = "idCategory", source="idCategory")
    @Mapping(target = "idRestaurant", source="idRestaurant")
    Dish toDish(DishRequestDto dishRequestDto);
}
