package com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishUpdateRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.IDishHandler;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper.IDishRequestMapper;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper.IDishResponseMapper;
import com.pragma.powerup.squaremicroservice.domain.api.IDishServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DishHandlerImpl implements IDishHandler {

    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;
    private final IDishResponseMapper dishResponseMapper;
    @Override
    public void saveDish(DishRequestDto dishRequestDto) {
        dishServicePort.saveDish(dishRequestMapper.toDish(dishRequestDto));
    }

    @Override
    public void updateDish(Long id, DishUpdateRequestDto dishUpdateRequestDto) {
        dishServicePort.updateDish(id,dishRequestMapper.toDish(dishUpdateRequestDto));
    }

    @Override
    public void enableDisableDish(Long id) {
        dishServicePort.enableDisableDish(id);
    }

    @Override
    public List<DishResponseDto> getDishes(int page, int pageSize) {
        return dishResponseMapper.toResponseList(dishServicePort. getDishes(page, pageSize));

    }
}
