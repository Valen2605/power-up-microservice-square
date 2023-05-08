package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.adapter;


import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantAlreadyExistsException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RestaurantMysqlAdapterTest {

    private RestaurantMysqlAdapter adapter;

    @Mock
    private IRestaurantRepository restaurantRepository;

    @Mock
    private IRestaurantEntityMapper restaurantEntityMapper;

    @BeforeEach
    public void restaurant() {
        MockitoAnnotations.openMocks(this);
        adapter = new RestaurantMysqlAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Test
    public void saveRestaurant_shouldSaveIfNotExists() {
        // Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setDniNumber("123456");
        when(restaurantRepository.findByDniNumber("123456")).thenReturn(Optional.empty());

        // Act
        adapter.saveRestaurant(restaurant);

        // Assert
        verify(restaurantRepository).save(any());
    }

}