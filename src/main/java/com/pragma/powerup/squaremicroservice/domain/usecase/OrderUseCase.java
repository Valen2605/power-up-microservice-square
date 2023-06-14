package com.pragma.powerup.squaremicroservice.domain.usecase;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.squaremicroservice.configuration.security.Interceptor;
import com.pragma.powerup.squaremicroservice.domain.api.IOrderServicePort;
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



    public OrderUseCase(IOrderRepository orderRepository, IOrderPersistencePort orderPersistencePort, IRestaurantPersistencePort restaurantPersistencePort) {

        this.orderPersistencePort = orderPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
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
    public List<Order> getOrders(String status, Long idRestaurant, int page, int pageSize) {
        return orderPersistencePort.getOrders(status, idRestaurant, page, pageSize);
    }
}
