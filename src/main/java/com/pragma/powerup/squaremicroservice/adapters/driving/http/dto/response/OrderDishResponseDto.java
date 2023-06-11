package com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderDishResponseDto {
    private Long idOrder;
    private Long idDish;
    private Integer quantity;
}
