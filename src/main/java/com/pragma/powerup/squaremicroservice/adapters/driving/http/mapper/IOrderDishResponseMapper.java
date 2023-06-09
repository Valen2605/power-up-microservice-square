package com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response.OrderDishResponseDto;
import com.pragma.powerup.squaremicroservice.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderDishResponseMapper {
    @Mapping(target = "idDish", source = "idDish")
    @Mapping(target = "idOrder", source = "idOrder")
    OrderDishResponseDto toResponse(OrderDish orderDish);
    List<OrderDishResponseDto> toResponseList(List<OrderDish> OrderDishList);
}
