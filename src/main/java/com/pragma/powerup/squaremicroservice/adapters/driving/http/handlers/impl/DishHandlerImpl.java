package com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.IDishHandler;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper.IDishRequestMapper;
import com.pragma.powerup.squaremicroservice.domain.api.IDishServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DishHandlerImpl implements IDishHandler {

    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;
    @Override
    public void saveDish(DishRequestDto dishRequestDto) {
        dishServicePort.saveDish(dishRequestMapper.toDish(dishRequestDto));
    }

    @Override
    public void updateDish(Long id, DishRequestDto dishRequestDto) {
        dishServicePort.updateDish(id,dishRequestMapper.toDish(dishRequestDto));
    }
}
