package com.pragma.powerup.squaremicroservice.domain.usecase;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.squaremicroservice.domain.exceptions.OrderIsNotPreparationException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.OrderNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IEmployeeRepository;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.squaremicroservice.configuration.security.Interceptor;
import com.pragma.powerup.squaremicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.squaremicroservice.domain.exceptions.UserNotBeAEmployeeException;
import com.pragma.powerup.squaremicroservice.domain.model.Order;
import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;


import com.pragma.powerup.squaremicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.utility.StatusEnum;


import java.time.LocalDate;
import java.util.List;


public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IOrderRepository orderRepository;

    private final IEmployeeRepository employeeRepository;



    public OrderUseCase(IOrderRepository orderRepository, IOrderPersistencePort orderPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IEmployeeRepository employeeRepository) {
        this.orderRepository = orderRepository;
        this.orderPersistencePort = orderPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public void saveOrder(Long idRestaurant) {
        Restaurant restaurant = restaurantPersistencePort.findById(idRestaurant);
        Order order = new Order();
        order.setIdClient(Interceptor.getIdUser());
        order.setDateOrder(LocalDate.now());
        order.setRestaurant(restaurant);
        order.setStatus(StatusEnum.PENDIENTE.toString());
        orderPersistencePort.saveOrder(order);

    }

    @Override
    public void assignOrder(Long id, Order order) {

        if(!employeeRepository.existsByIdEmployee(order.getIdChef())){
            throw new UserNotBeAEmployeeException();
        }

        orderPersistencePort.assignOrder(id, order);
    }

    @Override
    public void updateOrderReady(Long id, StatusEnum status) {
        String s = status.toString();
        OrderEntity orderEntityReady = orderRepository.findByIdAndStatus(id,s).orElseThrow(OrderNotFoundException::new);
        if(!orderEntityReady.getStatus().contains(StatusEnum.EN_PREPARACION.toString())) {
            throw new OrderIsNotPreparationException();
        }

        orderEntityReady.setCodeOrder("4950");
        orderPersistencePort.updateOrderReady(id, status);
    }

    @Override
    public List<Order> getOrders(String status, Long idRestaurant, int page, int pageSize) {
        return orderPersistencePort.getOrders(status, idRestaurant, page, pageSize);
    }
}
