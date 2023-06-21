package com.pragma.powerup.squaremicroservice.domain.usecase;


import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.OrderNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IEmployeeRepository;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.squaremicroservice.configuration.security.Interceptor;
import com.pragma.powerup.squaremicroservice.domain.exceptions.*;
import com.pragma.powerup.squaremicroservice.domain.model.Order;
import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.squaremicroservice.domain.model.User;
import com.pragma.powerup.squaremicroservice.domain.spi.*;
import com.pragma.powerup.squaremicroservice.domain.utility.StatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Mock
    private IClientHttpAdapterPersistencePort clientHttpAdapterPersistencePort;

    @Mock
    private IMessagingTwilioHttpAdapterPersistencePort messagingTwilioHttpAdapterPersistencePort;

    @Mock
    private ITraceabilityHttpAdapterPersistencePort traceabilityHttpAdapterPersistencePort;

    @Mock
    private RestTemplate restTemplate;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderUseCase = new OrderUseCase(orderRepository, orderPersistencePort, restaurantPersistencePort, employeeRepository, clientHttpAdapterPersistencePort, messagingTwilioHttpAdapterPersistencePort, traceabilityHttpAdapterPersistencePort);
        restTemplate = new RestTemplate();
    }

    @Test
    void getAllOrdersValidPage() {
        // Arrange
        int page = 1;
        int pageSize = 3;
        Long idRestaurant = 5L;
        String status = StatusEnum.PENDIENTE.toString();

        List<Order> orders = Arrays.asList(
                new Order(13L, 2L, LocalDate.of(2023, 6, 14),"PENDIENTE",1L, new Restaurant(13L, "Mi Restaurante 1", "carrera 13 #12-12","456789","image.jpg",1L, "123456"),"1234"),
                new Order(14L, 3L,LocalDate.of(2023, 6, 14),"PENDIENTE",1L,new Restaurant(14L, "Mi Restaurante 2", "carrera 13 #12-12","456789","image.jpg",1L, "123456"),"5678"),
                new Order(15L, 4L,LocalDate.of(2023, 6, 14),"PENDIENTE",1L,new Restaurant(14L, "Mi Restaurante 2", "carrera 13 #12-12","456789","image.jpg",1L, "123456"),"9012")
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

    @Test
    void assignOrderWhenChefNotExists() {
        // Arrange
        Long id = 1L;
        Order order = new Order();
        order.setIdChef(2L);

        when(employeeRepository.existsByIdEmployee(order.getIdChef())).thenReturn(false);

        // Act and Assert
        assertThrows(UserNotBeAEmployeeException.class, () -> orderUseCase.assignOrder(id, order));

        // Verify
        verify(orderPersistencePort, never()).assignOrder(anyLong(), any(Order.class));
    }

    @Test
    void assignOrderWhenChefExists() {
        // Arrange
        Long id = 1L;
        Order order = new Order();
        Long chefId = 456L;
        Long clientId = 789L;
        Interceptor.setIdUser(456L);

        StatusEnum status = StatusEnum.PENDIENTE;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(id);
        orderEntity.setIdChef(chefId);
        orderEntity.setIdClient(clientId);
        orderEntity.setStatus(StatusEnum.PENDIENTE.toString());

        when(employeeRepository.existsByIdEmployee(order.getIdChef())).thenReturn(true);
        when(orderRepository.findByIdAndStatus(id, status.toString())).thenReturn(Optional.of(orderEntity));

        // Act
        orderUseCase.assignOrder(id, order);

        // Assert
        verify(orderPersistencePort, times(1)).assignOrder(id, order);
    }


    @Test
    void testUpdateOrderReady() {
        // Arrange
        Long id = 1L;
        StatusEnum status = StatusEnum.EN_PREPARACION;

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(id);
        orderEntity.setStatus(status.toString());
        orderEntity.setIdClient(2L);
        Interceptor.setIdUser(456L);

        User user = new User();
        user.setPhone("1234567890");

        when(orderRepository.findByIdAndStatus(id, status.toString())).thenReturn(Optional.of(orderEntity));
        when(clientHttpAdapterPersistencePort.getClient(orderEntity.getIdClient())).thenReturn(user);

        // Act
        orderUseCase.updateOrderReady(id, status);

        // Assert
        verify(orderRepository).findByIdAndStatus(id, status.toString());
        verify(clientHttpAdapterPersistencePort).getClient(orderEntity.getIdClient());
        verify(messagingTwilioHttpAdapterPersistencePort).getMessaging(anyString(), eq(user.getPhone()));
        verify(orderPersistencePort).updateOrderReady(id, status);
    }


    @Test
    void testSaveOrderStatusDelivered() {
        // Arrange
        Long idRestaurant = 1L;
        Long idClient = 2L;
        Order order = new Order();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(idRestaurant);
        order.setIdClient(idClient);
        order.setDateOrder(LocalDate.now());
        order.setStatus(StatusEnum.ENTREGADO.toString());
        order.setRestaurant(restaurant);
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(idRestaurant);
        OrderEntity orderEntity = new OrderEntity(1L,idClient,LocalDate.now(),StatusEnum.ENTREGADO.toString(),2L,restaurantEntity, "ABC1234567");

        Interceptor.setIdUser(2L);
        // Act
        doNothing().when(orderPersistencePort).updateOrderDelivered(orderEntity.getId(),StatusEnum.ENTREGADO);


        // Assert
        assertEquals(idClient, order.getIdClient());
        assertEquals(LocalDate.now(), order.getDateOrder());
        assertEquals(StatusEnum.ENTREGADO.toString(), order.getStatus());
        assertEquals(restaurant, order.getRestaurant());
        verify(orderPersistencePort, times(0)).updateOrderDelivered(orderEntity.getId(),StatusEnum.ENTREGADO);
    }

    @Test
    void testUpdateOrderDeliveredNotExistingOrder() {
        // Arrange
        Long id = 1L;
        StatusEnum status = StatusEnum.ENTREGADO;
        String codeOrder = "ABC123";

        when(orderRepository.findByIdAndStatus(id, status.toString())).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(OrderNotFoundException.class, () -> {
            orderUseCase.updateOrderDelivered(id, status, codeOrder);
        });
    }

    @Test
    void testUpdateOrderDeliveredOrderNotReady() {
        // Arrange
        Long id = 1L;
        StatusEnum status = StatusEnum.PENDIENTE;
        String codeOrder = "ABC123";

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus(status.toString());

        when(orderRepository.findByIdAndStatus(id, status.toString())).thenReturn(Optional.of(orderEntity));

        // Act & Assert
        Assertions.assertThrows(OrderIsNotReadyException.class, () -> {
            orderUseCase.updateOrderDelivered(id, status, codeOrder);
        });
    }

    @Test
    void testUpdateOrderDeliveredIncorrectCode() {
        // Arrange
        Long id = 1L;
        StatusEnum status = StatusEnum.ENTREGADO;
        String codeOrder = "ABC123";

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus(StatusEnum.LISTO.toString());
        orderEntity.setCodeOrder(codeOrder);
        when(orderRepository.findByIdAndStatus(id, status.toString())).thenReturn(Optional.of(orderEntity));

        // Act & Assert
        Assertions.assertThrows(IncorrectCodeException.class, () -> {
            orderUseCase.updateOrderDelivered(id, status, "XYZ789");
        });
    }


    @Test
    void updateOrderCanceledOrderNotFoundException() {
        // Arrange
        Long orderId = 1L;
        StatusEnum status = StatusEnum.CANCELADO;

        when(orderRepository.findByIdAndStatus(anyLong(), anyString()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OrderNotFoundException.class,
                () -> orderUseCase.updateOrderCanceled(orderId, status));
    }


    @Test
    void updateOrderCanceledOrderIsInPreparationOrReadyOrDelivered() {
        // Arrange
        Long orderId = 1L;
        StatusEnum status = StatusEnum.CANCELADO;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus(StatusEnum.EN_PREPARACION.toString());
        orderEntity.setIdClient(1L);

        User user = new User();
        user.setPhone("1234567890");

        when(orderRepository.findByIdAndStatus(anyLong(), anyString()))
                .thenReturn(Optional.of(orderEntity));

        when(clientHttpAdapterPersistencePort.getClient(orderEntity.getIdClient())).thenReturn(user);

        // Act & Assert
        assertThrows(OrderNotCanceledException.class,
                () -> orderUseCase.updateOrderCanceled(orderId, status));

        verify(messagingTwilioHttpAdapterPersistencePort).getMessaging(eq("Lo sentimos, tu pedido ya está en preparación y no puede cancelarse"),
                eq("1234567890"));
    }


    @Test
    void updateOrderCanceledwhenOrderIsAlreadyCancelled() {
        // Arrange
        Long orderId = 1L;
        StatusEnum status = StatusEnum.CANCELADO;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus(StatusEnum.CANCELADO.toString());

        when(orderRepository.findByIdAndStatus(anyLong(), anyString()))
                .thenReturn(Optional.of(orderEntity));

        // Act & Assert
        assertThrows(OrderAlreadyCancelledException.class,
                () -> orderUseCase.updateOrderCanceled(orderId, status));
    }

    @Test
    void testSaveOrderStatusCancelled() {
        // Arrange
        Long idRestaurant = 1L;
        Long idClient = 2L;
        Order order = new Order();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(idRestaurant);
        order.setIdClient(idClient);
        order.setDateOrder(LocalDate.now());
        order.setStatus(StatusEnum.PENDIENTE.toString());
        order.setRestaurant(restaurant);
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(idRestaurant);
        OrderEntity orderEntity = new OrderEntity(1L,idClient,LocalDate.now(),StatusEnum.CANCELADO.toString(),2L,restaurantEntity,"ABC1234567");

        when(restaurantPersistencePort.findById(idRestaurant)).thenReturn(restaurant);
        doNothing().when(orderPersistencePort).updateOrderCanceled(orderEntity.getId(), StatusEnum.CANCELADO);
        Interceptor.setIdUser(2L);
        // Act
        orderUseCase.saveOrder(idRestaurant);


        // Assert
        verify(restaurantPersistencePort, times(1)).findById(idRestaurant);
        assertEquals(idClient, order.getIdClient());
        assertEquals(LocalDate.now(), order.getDateOrder());
        assertEquals(StatusEnum.PENDIENTE.toString(), order.getStatus());
        assertEquals(restaurant, order.getRestaurant());
        verify(orderPersistencePort, times(0)).updateOrderCanceled(orderEntity.getId(), StatusEnum.CANCELADO);
    }


}