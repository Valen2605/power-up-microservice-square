package com.pragma.powerup.squaremicroservice.domain.usecase;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.DishOrderEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IDishOrderRepository;
import com.pragma.powerup.squaremicroservice.domain.model.Dessert;
import com.pragma.powerup.squaremicroservice.domain.model.DishOrder;
import com.pragma.powerup.squaremicroservice.domain.model.Meat;
import com.pragma.powerup.squaremicroservice.domain.model.Soup;
import com.pragma.powerup.squaremicroservice.domain.spi.IDishOrderPersistencePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DishOrderUseCaseTest {

    private DishOrderUseCase dishOrderUseCase;

    @Mock
    private IDishOrderPersistencePort dishOrderPersistencePort;

    @Mock
    private IDishOrderRepository dishOrderRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        dishOrderUseCase = new DishOrderUseCase(dishOrderPersistencePort, dishOrderRepository);
    }

    @Test
    void testSaveDishOrder_withMeatDishOrderAndGramsEquals750() {
        // Arrange
        Meat meatDishOrder = Mockito.mock(Meat.class);
        Mockito.when(meatDishOrder.getGrams()).thenReturn(750);

        // Act
        dishOrderUseCase.saveDishOrder(meatDishOrder);

        // Assert
        Mockito.verify(meatDishOrder).setPriority(1);
        Mockito.verify(dishOrderPersistencePort).saveDishOrder(meatDishOrder);
    }

    @Test
    void testSaveDishOrder_withMeatDishOrderAndGramsEqualsOtherValue() {
        // Arrange
        Meat meatDishOrder = Mockito.mock(Meat.class);
        Mockito.when(meatDishOrder.getGrams()).thenReturn(250);

        // Act
        dishOrderUseCase.saveDishOrder(meatDishOrder);

        // Assert
        Mockito.verify(meatDishOrder).setPriority(2);
        Mockito.verify(dishOrderPersistencePort).saveDishOrder(meatDishOrder);
    }

    @Test
    void testSaveDishOrder_withSoupDishOrderAndCompanionEqualsYuca() {
        // Arrange
        Soup soupDishOrder = Mockito.mock(Soup.class);
        Mockito.when(soupDishOrder.getCompanion()).thenReturn("yuca");

        // Act
        dishOrderUseCase.saveDishOrder(soupDishOrder);

        // Assert
        Mockito.verify(soupDishOrder).setPriority(3);
        Mockito.verify(dishOrderPersistencePort).saveDishOrder(soupDishOrder);
    }

    @Test
    void testSaveDishOrder_withSoupDishOrderAndCompanionEqualsPapa() {
        // Arrange
        Soup soupDishOrder = Mockito.mock(Soup.class);
        Mockito.when(soupDishOrder.getCompanion()).thenReturn("papa");

        // Act
        dishOrderUseCase.saveDishOrder(soupDishOrder);

        // Assert
        Mockito.verify(soupDishOrder).setPriority(4);
        Mockito.verify(dishOrderPersistencePort).saveDishOrder(soupDishOrder);
    }

    @Test
    void testSaveDishOrder_withSoupDishOrderAndCompanionEqualsArroz() {
        // Arrange
        Soup soupDishOrder = Mockito.mock(Soup.class);
        Mockito.when(soupDishOrder.getCompanion()).thenReturn("arroz");

        // Act
        dishOrderUseCase.saveDishOrder(soupDishOrder);

        // Assert
        Mockito.verify(soupDishOrder).setPriority(5);
        Mockito.verify(dishOrderPersistencePort).saveDishOrder(soupDishOrder);
    }

    @Test
    void testSaveDishOrder_withDessertDishOrderFlan() {
        // Arrange
        Dessert dessertDishOrder = Mockito.mock(Dessert.class);
        Mockito.when(dessertDishOrder.getTypeDessert()).thenReturn("flan");

        // Act
        dishOrderUseCase.saveDishOrder(dessertDishOrder);

        // Assert
        Mockito.verify(dessertDishOrder).setPriority(6);
        Mockito.verify(dishOrderPersistencePort).saveDishOrder(dessertDishOrder);
    }

    @Test
    void testSaveDishOrder_withDessertDishOrderIceCream() {
        // Arrange
        Dessert dessertDishOrder = Mockito.mock(Dessert.class);
        Mockito.when(dessertDishOrder.getTypeDessert()).thenReturn("helado");

        // Act
        dishOrderUseCase.saveDishOrder(dessertDishOrder);

        // Assert
        Mockito.verify(dessertDishOrder).setPriority(7);
        Mockito.verify(dishOrderPersistencePort).saveDishOrder(dessertDishOrder);
    }



    @Test
    void testSaveDishOrder_withUnknownDishOrder_shouldNotSetPriorityAndCallPersistencePort() {
        // Arrange
        DishOrder dishOrder = Mockito.mock(DishOrder.class);

        // Act
        dishOrderUseCase.saveDishOrder(dishOrder);

        // Assert
        Mockito.verify(dishOrder, Mockito.never()).setPriority(Mockito.anyInt());
        Mockito.verify(dishOrderPersistencePort).saveDishOrder(dishOrder);
    }


    @Test
    void saveDishOrdersTest() {
        List<DishOrder> dishList = Arrays.asList(new DishOrder(), new DishOrder());

        // Act
        dishOrderUseCase.saveDishOrders(dishList);

        // Assert
        verify(dishOrderPersistencePort, times(2)).saveDishOrder(any(DishOrder.class));
    }


    @Test
    void testGetOrders() {
        // Arrange
        List<DishOrder> orderDishList = new ArrayList<>();
        orderDishList.add(new DishOrder());
        Mockito.when(dishOrderPersistencePort.getOrders()).thenReturn(orderDishList);

        List<DishOrderEntity> orderDishEntity = new ArrayList<>();
        orderDishEntity.add(new DishOrderEntity(1L, "Sopa", "Yuca",5, 2, "No aplica", "No aplica","No aplica"));
        Mockito.when(dishOrderRepository.findAll()).thenReturn(orderDishEntity);

        // Act
        List<DishOrder> result = dishOrderUseCase.getOrders();

        // Assert
        assertEquals(orderDishList, result);
        Mockito.verify(dishOrderPersistencePort, Mockito.times(1)).getOrders();
        Mockito.verify(dishOrderRepository, Mockito.times(1)).findAll();
    }

    @Test
    void deleteDishOrder() {
        // Arrange
        Long orderId = 1L;

        // Act
        dishOrderUseCase.deleteDishOrder(orderId);

        // Assert
        verify(dishOrderRepository).deleteById(orderId);
    }


    @Test
    void pendingOrders() {
        // Arrange
        List<DishOrder> expectedOrders = new ArrayList<>();
        expectedOrders.add(new DishOrder());
        expectedOrders.add(new DishOrder());
        when(dishOrderPersistencePort.pendingOrders()).thenReturn(expectedOrders);

        // Act
        List<DishOrder> actualOrders = dishOrderUseCase.pendingOrders();

        // Assert
        assertEquals(expectedOrders, actualOrders);
    }
}