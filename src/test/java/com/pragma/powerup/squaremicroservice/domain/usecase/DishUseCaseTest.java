package com.pragma.powerup.squaremicroservice.domain.usecase;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.adapter.DishMysqlAdapter;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.adapter.OwnerHttpAdapter;
import com.pragma.powerup.squaremicroservice.configuration.Constants;
import com.pragma.powerup.squaremicroservice.domain.exceptions.UserNotBeAOwnerException;
import com.pragma.powerup.squaremicroservice.domain.model.Category;
import com.pragma.powerup.squaremicroservice.domain.model.Dish;
import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.squaremicroservice.domain.model.User;
import com.pragma.powerup.squaremicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {

    @Mock
    private IDishPersistencePort dishPersistencePort;
    @Mock
    private DishMysqlAdapter dishMysqlAdapter;
    @InjectMocks
    private DishUseCase dishUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSaveDish() {
        // Arrange
        Dish dish = new Dish();
        dish.setName("Arroz Chino");
        dish.setCategory(new Category());
        dish.setDescription("Contiene raíces chinas");
        dish.setPrice(45000);
        dish.setRestaurant(new Restaurant());
        dish.setUrlImage("imagen.jpg");

        dishPersistencePort.saveDish(dish);

        // Act
        dishUseCase.saveDish(dish);

        // Assert
        Mockito.verify(dishPersistencePort, times(1)).saveDish(dish);
    }

    @Test
    public void testUpdateDish() {
        // Arrange
        Long id = 1L;
        Dish dish = new Dish();
        dish.setDescription("Contiene camarones");
        dish.setPrice(48000);

        // Act
        dishPersistencePort.updateDish(id, dish);

        // Assert
        Mockito.verify(dishPersistencePort).updateDish(id, dish);
    }

}