package com.pragma.powerup.squaremicroservice.domain.usecase;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.squaremicroservice.configuration.security.Interceptor;
import com.pragma.powerup.squaremicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.squaremicroservice.domain.exceptions.PageNotFoundException;
import com.pragma.powerup.squaremicroservice.domain.exceptions.UserNotBeAOwnerException;
import com.pragma.powerup.squaremicroservice.domain.model.Dish;
import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.squaremicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.utility.Pagination;


import java.util.Comparator;
import java.util.List;
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
        if(!restaurantRepository.findById(idRestaurant).isPresent()){
            throw new RestaurantNotFoundException();
        }
        Optional<RestaurantEntity> restaurant = restaurantRepository.findById(idRestaurant);
        Long userId = Interceptor.getIdUser();
        if (!restaurant.get().getIdOwner().equals(userId)) {
            throw new UserNotBeAOwnerException();
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

    @Override
    public void enableDisableDish(Long id) {
        DishEntity dish = dishRepository.findById(id).orElseThrow(DishNotFoundException::new);
        validateOwner(dish.getRestaurantEntity().getId());
        dishRepository.save(dish);
        dishPersistencePort.enableDisableDish(id);

    }

    @Override
    public List<Dish> getDishes(int page, int pageSize) {
        if (page>0){
            List<Dish> dishes = dishPersistencePort.getDishes(page, pageSize);
            dishes.sort(Comparator.comparing(Dish::getName));
            return  Pagination.paginate(dishes, pageSize, page);
        }
        throw new PageNotFoundException();
    }


}
