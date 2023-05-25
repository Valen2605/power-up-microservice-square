package com.pragma.powerup.squaremicroservice.domain.usecase;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.NoRestaurantOwnerException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.squaremicroservice.configuration.security.Interceptor;
import com.pragma.powerup.squaremicroservice.domain.api.IDishServicePort;

import com.pragma.powerup.squaremicroservice.domain.model.Dish;
import com.pragma.powerup.squaremicroservice.domain.spi.IDishPersistencePort;

import java.util.Optional;

public class DishUseCase implements IDishServicePort {

    private final IRestaurantRepository restaurantRepository;

    private final IDishRepository dishRepository;

    private final IDishPersistencePort dishPersistencePort;


    public DishUseCase(IRestaurantRepository restaurantRepository, IDishRepository dishRepository, IDishPersistencePort dishPersistencePort) {
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
        this.dishPersistencePort = dishPersistencePort;
    }

    public void validateOwner(Long idRestaurant){
        Interceptor interceptor = new Interceptor();
        Optional<RestaurantEntity> restaurant = Optional.of(new RestaurantEntity());
         if(!restaurantRepository.findById(idRestaurant).isPresent()){
             throw new RestaurantNotFoundException();
         }
         restaurant = restaurantRepository.findById(idRestaurant);
        if(!interceptor.getIdUser().equals(restaurant.get().getIdOwner())){
            throw new NoRestaurantOwnerException();
        }
    }

    @Override
    public void saveDish(Dish dish){
        validateOwner(dish.getRestaurant().getId());
        dish.setActive(true);
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public void updateDish(Long id,Dish dish){
        DishEntity dishEntityUpdate = dishRepository.findById(id).orElseThrow(DishNotFoundException::new);
        validateOwner(dishEntityUpdate.getRestaurantEntity().getId());
        dishPersistencePort.updateDish(id,dish);
    }


}
