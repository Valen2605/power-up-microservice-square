package com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.CategoryRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.ICategoryHandler;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.pragma.powerup.squaremicroservice.domain.api.ICategoryServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CategoryHandlerImpl implements ICategoryHandler {

    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;
    @Override
    public void saveCategory(CategoryRequestDto categoryRequestDto) {
        categoryServicePort.saveCategory(categoryRequestMapper.toCategory(categoryRequestDto));
    }
}
