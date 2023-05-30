package com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishUpdateRequestDto;

public interface IDishHandler {
    void saveDish(DishRequestDto dishRequestDto);

    void updateDish(Long id, DishUpdateRequestDto dishUpdateRequestDto);

    void enableDisableDish(Long id);
}
