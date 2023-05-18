package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.squaremicroservice.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishEntityMapper {

    @Mapping(target = "categoryEntity.id", source = "category.id")
    @Mapping(target = "restaurantEntity.id", source = "restaurant.id")
    DishEntity toEntity(Dish dish);
    List<Dish> toDishList(List<DishEntity> dishEntityList);

}
