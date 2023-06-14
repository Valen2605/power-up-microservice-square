package com.pragma.powerup.squaremicroservice.domain.usecase;


import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IEmployeeRepository;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.squaremicroservice.domain.model.Order;
import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.squaremicroservice.domain.spi.IEmployeeHttpAdapterPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.utility.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderUseCaseTest {

    private OrderUseCase orderUseCase;

    @Mock
    private IOrderPersistencePort orderPersistencePort;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IEmployeeRepository employeeRepository;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderUseCase = new OrderUseCase(orderRepository, orderPersistencePort, restaurantPersistencePort, employeeRepository);
    }

    @Test
    void getAllOrdersValidPage() {
        // Arrange
        int page = 1;
        int pageSize = 3;
        Long idRestaurant = 5L;
        String status = StatusEnum.PENDIENTE.toString();

        List<Order> orders = Arrays.asList(
                new Order(13L, 2L, LocalDate.of(2023, 6, 14),"PENDIENTE",1L, new Restaurant(13L, "Mi Restaurante 1", "carrera 13 #12-12","456789","image.jpg",1L, "123456")),
                new Order(14L, 3L,LocalDate.of(2023, 6, 14),"PENDIENTE",1L,new Restaurant(14L, "Mi Restaurante 2", "carrera 13 #12-12","456789","image.jpg",1L, "123456")),
                new Order(15L, 4L,LocalDate.of(2023, 6, 14),"PENDIENTE",1L,new Restaurant(14L, "Mi Restaurante 2", "carrera 13 #12-12","456789","image.jpg",1L, "123456"))
                );

        when(orderPersistencePort.getOrders(status,idRestaurant,page,pageSize)).thenReturn(orders);


        // Act
        List<Order> result = orderPersistencePort.getOrders(status,idRestaurant,page,pageSize);

        // Assert
        verify(orderPersistencePort, times(1)).getOrders(status, idRestaurant,page, pageSize);
        assertEquals(pageSize, result.size());
        assertEquals("PENDIENTE", result.get(0).getStatus());
        assertEquals("PENDIENTE", result.get(1).getStatus());
        assertEquals("PENDIENTE", result.get(2).getStatus());
    }

}