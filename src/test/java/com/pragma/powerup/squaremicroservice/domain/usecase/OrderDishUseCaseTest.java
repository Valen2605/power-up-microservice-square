package com.pragma.powerup.squaremicroservice.domain.usecase;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundInRestaurantException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.OrderNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.squaremicroservice.domain.model.OrderDish;
import com.pragma.powerup.squaremicroservice.domain.spi.IOrderDishPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class OrderDishUseCaseTest {

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IDishRepository dishRepository;

    @Mock
    private IOrderDishPersistencePort orderDishPersistencePort;

    @InjectMocks
    private OrderDishUseCase orderDishUseCase;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        orderDishUseCase = new OrderDishUseCase(orderDishPersistencePort,orderRepository,dishRepository);
    }


    @Test
    void testSaveOrderDishSuccess() {

        OrderDish orderDish = new OrderDish();

        orderDish.setId(1L);

        // Act
        orderDishUseCase.saveOrderDish(orderDish);

        // Assert
        Mockito.verify(orderDishPersistencePort, Mockito.times(1)).saveOrderDish(orderDish);
    }

}