package com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response.RestaurantsResponseDto;
import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantResponseMapper {
    List<RestaurantsResponseDto> toResponseList(List<Restaurant> restaurantList);


}