package com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.squaremicroservice.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderResponseMapper {
    @Mapping(target = "idClient", source = "idClient")
    @Mapping(target = "idChef", source = "idChef")
    @Mapping(target = "idRestaurant", source = "restaurant.id")
    OrderResponseDto toResponse(Order order);
    List<OrderResponseDto> toResponseList(List<Order> orderList);
}
