package com.pragma.powerup.squaremicroservice.domain.usecase;


import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.squaremicroservice.configuration.security.Interceptor;
import com.pragma.powerup.squaremicroservice.domain.exceptions.UserNotBeAOwnerException;
import com.pragma.powerup.squaremicroservice.domain.model.Category;
import com.pragma.powerup.squaremicroservice.domain.model.Dish;
import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.squaremicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    private IRestaurantPersistencePort restaurantPersistencePort;


    @Mock
    private IDishPersistencePort dishPersistencePort;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        dishUseCase = new DishUseCase(restaurantRepository,dishRepository,dishPersistencePort );
    }


    @Test
    void testValidateIdOwner() {
        // Arrange
        Long idRestaurant = 1L;
        Long idOwnerToken = 123456789L;

        Interceptor.setIdUser(idOwnerToken);

        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setIdOwner(119129L);

        when(restaurantRepository.findById(idRestaurant)).thenReturn(Optional.of(restaurantEntity));

        // Act y Assert
        assertThrows(UserNotBeAOwnerException.class, () -> dishUseCase.validateOwner(idRestaurant));
    }

    @Test
    void testValidateIdOwner_NoExceptionThrown() {
        // Arrange
        Long idRestaurant = 1L;
        Long idOwnerToken = 123456789L;

        Interceptor.setIdUser(idOwnerToken);


        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setIdOwner(123456789L);


        when(restaurantRepository.findById(idRestaurant)).thenReturn(Optional.of(restaurantEntity));

        // Act y Assert
        dishUseCase.validateOwner(idRestaurant);
    }

    @Test
    void saveDish_WhenNewPlateWithValidOwner_ShouldSavePlateInRepository() {
        // Arrange
        Dish dish = new Dish(1L, "Arroz chino", new Category(), "Lleva verduras y raíces chinas", 15000,
                new Restaurant(1L, "Restaurante 1", "Calle 50", "3328752", "urlimagen.jpg", 2L, "1235156"), "url", true);
        RestaurantEntity restaurantEntity = new RestaurantEntity(1L, "Restaurante 1", "Calle 50", "3328752", "urlimagen.jpg", 2L, "1235156");

        Interceptor.setIdUser(2L);
        restaurantEntity.setIdOwner(Interceptor.getIdUser());
        when(restaurantRepository.existsById(dish.getRestaurant().getId())).thenReturn(true);
        when(restaurantRepository.findById(dish.getRestaurant().getId())).thenReturn(Optional.of(restaurantEntity));
        when(dishRepository.existsById(dish.getCategory().getId())).thenReturn(true);

        // Act
        dishUseCase.saveDish(dish);

        // Assert
        verify(restaurantRepository, atLeastOnce()).findById(dish.getRestaurant().getId());
        verify(dishPersistencePort).saveDish(dish);
    }


    @Test
    void saveDishWhenRestaurantNotFound() {
        // Arrange
        Dish dish = new Dish();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        dish.setRestaurant(restaurant);

        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

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

        Interceptor.setIdUser(2L);

        when(restaurantRepository.findById(dish.getRestaurant().getId())).thenReturn(Optional.of(restaurantEntity));
        when(dishRepository.findAllByRestaurantEntityId(restaurantEntity.getId())).thenReturn(new ArrayList<>());
        when(dishRepository.findAllByCategoryEntityId(dish.getCategory().getId())).thenReturn(new ArrayList<>());

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

        Interceptor.setIdUser(8L);
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


    @Test
    void enableDisableDishInvalidId() {
        // Arrange
        Long id = 1L;
        when(dishRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(DishNotFoundException.class, () -> {
            dishUseCase.enableDisableDish(id);
        });
        verify(dishRepository).findById(id);
        verifyNoMoreInteractions(dishRepository);
        verify(dishPersistencePort);
    }

    @Test
    void enableDisableDish_ValidId_DishEnabled() {
        // Arrange
        Long id = 1L;
        DishEntity dish = new DishEntity();
        dish.setId(id);
        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(1L);
        Interceptor.setIdUser(8L);
        restaurant.setIdOwner(8L);
        dish.setRestaurantEntity(restaurant);

        when(restaurantRepository.findById(dish.getRestaurantEntity().getId())).thenReturn(Optional.of(dish.getRestaurantEntity()));
        when(dishRepository.findById(id)).thenReturn(Optional.of(dish));

        // Act
        dishUseCase.enableDisableDish(id);

        // Assert
        verify(restaurantRepository,atLeastOnce()).findById(dish.getRestaurantEntity().getId());
        verify(dishRepository, times(1)).findById(id);
        verify(dishRepository, times(1)).save(dish);
        verify(dishPersistencePort, times(1)).enableDisableDish(id);
    }

    @Test
    void testGetDishes() {
        // Arrange
        Long idRestaurant = 1L;
        Long idCategory = 2L;
        int page = 1;
        int size = 10;

        List<Dish> expectedDishes = new ArrayList<>();
        expectedDishes.add(new Dish());
        Mockito.when(dishPersistencePort.getDishes(idRestaurant, idCategory, page, size))
                .thenReturn(expectedDishes);

        // Act
        List<Dish> actualDishes = dishUseCase.getDishes(idRestaurant, idCategory, page, size);

        // Assert
        assertEquals(expectedDishes, actualDishes);
    }


}