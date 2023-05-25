package com.pragma.powerup.squaremicroservice.domain.usecase;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.adapter.DishMysqlAdapter;
import com.pragma.powerup.squaremicroservice.domain.model.Category;
import com.pragma.powerup.squaremicroservice.domain.model.Dish;
import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.squaremicroservice.domain.spi.IDishPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


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

        Dish dish = new Dish();
        dish.setName("Arroz Chino");
        dish.setCategory(new Category());
        dish.setDescription("Contiene raíces chinas");
        dish.setPrice(45000);
        dish.setRestaurant(new Restaurant());
        dish.setUrlImage("imagen.jpg");

        dishPersistencePort.saveDish(dish);


        dishUseCase.saveDish(dish);

        // Assert
        Mockito.verify(dishPersistencePort, times(1)).saveDish(dish);
    }

    @Test
    void testUpdateDish() {

        Long id = 1L;
        Dish dish = new Dish();
        dish.setDescription("Contiene camarones");
        dish.setPrice(48000);


        dishPersistencePort.updateDish(id, dish);


        Mockito.verify(dishPersistencePort).updateDish(id, dish);
    }

}