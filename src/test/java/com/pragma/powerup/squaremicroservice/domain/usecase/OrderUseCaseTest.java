package com.pragma.powerup.squaremicroservice.domain.usecase;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.squaremicroservice.configuration.security.Interceptor;
import com.pragma.powerup.squaremicroservice.domain.model.Order;
import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.squaremicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.utility.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderUseCaseTest {
    private OrderUseCase orderUseCase;

    @Mock
    private IOrderPersistencePort orderPersistencePort;
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private Interceptor interceptor;

    @Mock
    IOrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderUseCase = new OrderUseCase(orderRepository,orderPersistencePort, restaurantPersistencePort);
    }

    @Test
    void saveOrderWhenOrderWithCorrectParameters() {
        // Arrange
        Long idRestaurant = 123L;
        Long idUser = 456L;
        Restaurant restaurant = new Restaurant();
        when(restaurantPersistencePort.findById(idRestaurant)).thenReturn(restaurant);
        when(interceptor.getIdUser()).thenReturn(456L);

        // Act
        orderUseCase.saveOrder(idRestaurant);

        // Assert
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderPersistencePort).saveOrder(orderCaptor.capture());

        Order savedOrder = orderCaptor.getValue();
        assertEquals(idUser, savedOrder.getIdClient());
        assertEquals(LocalDate.now(), savedOrder.getDate());
        assertEquals(StatusEnum.PENDIENTE.toString(), savedOrder.getStatus());
        assertEquals(restaurant, savedOrder.getRestaurant());

        verify(restaurantPersistencePort).findById(idRestaurant);
        verifyNoMoreInteractions(orderPersistencePort, restaurantPersistencePort);
    }

}