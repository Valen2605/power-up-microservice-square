package com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class OrderDishRequestDto {

    @NotNull(message = "The Order must not be empty")
    private Long idOrder;

    @NotNull(message = "The Dish must not be empty")
    private Long idDish;

    @NotNull(message = "The quantity must not be empty")
    private Integer quantity;

}
