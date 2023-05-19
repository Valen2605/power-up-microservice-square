package com.pragma.powerup.squaremicroservice.domain.usecase;

import com.pragma.powerup.squaremicroservice.domain.api.IDishServicePort;

import com.pragma.powerup.squaremicroservice.domain.model.Dish;
import com.pragma.powerup.squaremicroservice.domain.spi.IDishPersistencePort;

public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;


    public DishUseCase(IDishPersistencePort dishPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
    }

    @Override
    public void saveDish(Dish dish){
        dish.setActive(true);
        dishPersistencePort.saveDish(dish);

    }

    @Override
    public void updateDish(Long id,Dish dish){
        dishPersistencePort.updateDish(id,dish);
    }


}
