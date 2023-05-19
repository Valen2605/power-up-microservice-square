package com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DishResponseDto {
    private String name;
    private Long idCategory;
    private String description;
    private Integer price;
    private Long idRestaurant;
    private String urlImage;
    private Boolean active;
}
