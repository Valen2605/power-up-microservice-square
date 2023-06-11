package com.pragma.powerup.squaremicroservice.domain.usecase;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundInRestaurantException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.OrderNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.squaremicroservice.domain.model.OrderDish;
import com.pragma.powerup.squaremicroservice.domain.spi.IOrderDishPersistencePort;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class OrderDishUseCaseTest {

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IOrderRepository dishRepository;

    @Mock
    private IOrderDishPersistencePort orderDishPersistencePort;

    @InjectMocks
    private OrderDishUseCase orderDishUseCase;

    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveOrderDishOrderNotFound() {
        // Arrange
        Long idOrder = 123L;
        Mockito.when(orderRepository.findRestaurantById(idOrder)).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(OrderNotFoundException.class, () -> {
            orderDishUseCase.saveOrderDish(new OrderDish());
        });
    }

    @Test
    public void testSaveOrderDishWhenDishNotFoundInRestaurant() {
        // Arrange
        Long idOrder = 123L;
        List<OrderEntity> idRestaurant = new ArrayList<>();
        idRestaurant.add(new OrderEntity());
        Mockito.when(orderRepository.findRestaurantById(idOrder)).thenReturn(idRestaurant);

        // Act & Assert
        assertThrows(DishNotFoundInRestaurantException.class, () -> {
            orderDishUseCase.saveOrderDish(new OrderDish());
        });
    }

    @Test
    public void testSaveOrderDishSuccess() {
        // Arrange
        Long idOrder = 123L;
        List<OrderEntity> idRestaurant = new ArrayList<>();
        idRestaurant.add(new OrderEntity());
        Mockito.when(orderRepository.findRestaurantById(idOrder)).thenReturn(idRestaurant);


        // Act
        orderDishUseCase.saveOrderDish(new OrderDish());

        // Assert
        Mockito.verify(orderDishPersistencePort, Mockito.times(1)).saveOrderDish(Mockito.any(OrderDish.class));
    }

}