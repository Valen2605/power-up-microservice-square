package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantAlreadyExistsException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.squaremicroservice.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
public class RestaurantMysqlAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    @Override
    public void saveRestaurant(Restaurant restaurant) {

        if (restaurantRepository.findByDniNumber(restaurant.getDniNumber()).isPresent()) {
            throw new RestaurantAlreadyExistsException();
        }
        restaurantRepository.save(restaurantEntityMapper.toEntity(restaurant));
    }

    @Override
    public List<Restaurant> getAllRestaurants(int page, int pageSize) {
        List<RestaurantEntity> restaurantEntities = restaurantRepository.findAll();
        if (restaurantEntities.isEmpty()) {
            throw new RestaurantNotFoundException();
        }
        return restaurantEntityMapper.toRestaurantList(restaurantEntities);


    }
}
