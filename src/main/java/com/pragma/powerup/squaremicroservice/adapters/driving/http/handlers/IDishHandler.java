package com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishRequestDto;

public interface IDishHandler {
    void saveDish(DishRequestDto dishRequestDto);
}
