package com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.CategoryRequestDto;

public interface ICategoryHandler {
    void saveCategory(CategoryRequestDto categoryRequestDto);

}
