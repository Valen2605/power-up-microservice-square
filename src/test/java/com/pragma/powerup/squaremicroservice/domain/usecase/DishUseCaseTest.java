package com.pragma.powerup.squaremicroservice.domain.usecase;


import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.squaremicroservice.domain.model.Category;
import com.pragma.powerup.squaremicroservice.domain.model.Dish;
import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.squaremicroservice.domain.spi.IDishPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class DishUseCaseTest {


    private DishUseCase dishUseCase;

    @Mock
    private IRestaurantRepository restaurantRepository;
    @Mock
    private IDishRepository dishRepository;



    @Mock
    private IDishPersistencePort dishPersistencePort;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        dishUseCase = new DishUseCase(restaurantRepository,dishRepository,dishPersistencePort );
    }

    @Test
    void saveDishWhenDishAlreadyExists() {
        // Arrange
        Dish dish = new Dish();
        dish.setName("Arroz con pollo");
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        dish.setRestaurant(restaurant);

        DishEntity existingDishEntity = new DishEntity();
        existingDishEntity.setName("Arroz con pollo");

        List<DishEntity> existingDishes = new ArrayList<>();
        existingDishes.add(existingDishEntity);

        when(dishRepository.findAllByRestaurantEntityId(restaurant.getId())).thenReturn(existingDishes);

        // Act & Assert
        assertThrows(RestaurantNotFoundException.class, () -> dishUseCase.saveDish(dish));
    }

    @Test
    void saveDishWhenRestaurantNotFound() {
        // Arrange
        Dish dish = new Dish();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        dish.setRestaurant(restaurant);

        when(dishRepository.findAllByRestaurantEntityId(restaurant.getId())).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(RestaurantNotFoundException.class, () -> dishUseCase.saveDish(dish));
    }

    @Test
    void saveDishWhenValidDish() {
        // Arrange
        Dish dish = new Dish(1L, "Arroz chino", new Category(), "Lleva verduras y raíces chinas", 15000,
                new Restaurant(1L, "Restaurante 1", "Calle 50", "3328752", "urlimagen.jpg", 2L, "1235156"), "url", true);
        RestaurantEntity restaurantEntity = new RestaurantEntity(1L, "Restaurante 1", "Calle 50", "3328752", "urlimagen.jpg", 2L, "1235156");

        when(restaurantRepository.findById(dish.getRestaurant().getId())).thenReturn(Optional.of(restaurantEntity));

        // Act
        dishUseCase.saveDish(dish);

        // Assert
        verify(restaurantRepository,atLeastOnce()).findById(dish.getRestaurant().getId());
        verify(dishPersistencePort).saveDish(dish);
    }

    @Test
    void updateDishWhenDishNotFound() {
        // Arrange
        Long dishId = 1L;
        Dish dish = new Dish();

        when(dishRepository.findById(dishId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DishNotFoundException.class, () -> dishUseCase.updateDish(dishId, dish));
    }

    @Test
    void updateDishWhenValidDish() {
        // Arrange
        Long dishId = 1L;
        Dish updatedDish = new Dish(dishId, "Nuevo nombre", new Category(), "Nueva descripción", 20000,
                new Restaurant(1L, "Restaurante 1", "Calle 50", "3328752", "urlimagen.jpg", 8L, "1235156"), "url", true);
        DishEntity dishEntity = new DishEntity(dishId, "Arroz con verduras", new CategoryEntity(), "Lleva carne y pollo", 15000,
                new RestaurantEntity(1L, "Restaurante 1", "Calle 50", "3328752", "urlimagen.jpg", 8L, "1235156"), "url", true);
        Optional<DishEntity> dishEntityOptional = Optional.of(dishEntity);
        when(dishRepository.findById(dishId)).thenReturn(dishEntityOptional);
        when(restaurantRepository.findById(updatedDish.getRestaurant().getId())).thenReturn(Optional.of(dishEntity.getRestaurantEntity()));

        // Act
        dishUseCase.updateDish(dishId, updatedDish);

        // Assert
        verify(dishRepository).findById(dishId);
        verify(restaurantRepository, atLeastOnce()).findById(updatedDish.getRestaurant().getId());
        verify(dishPersistencePort).updateDish(dishId, updatedDish);
    }
}