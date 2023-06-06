package com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishUpdateRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response.DishResponseDto;

import java.util.List;

public interface IDishHandler {
    void saveDish(DishRequestDto dishRequestDto);

    void updateDish(Long id, DishUpdateRequestDto dishUpdateRequestDto);

    void enableDisableDish(Long id);

    List<DishResponseDto> getDishes(int page, int pageSize);
}
