package com.pragma.powerup.squaremicroservice.domain.usecase;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundInRestaurantException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.OrderNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.squaremicroservice.domain.api.IOrderDishServicePort;
import com.pragma.powerup.squaremicroservice.domain.model.OrderDish;
import com.pragma.powerup.squaremicroservice.domain.spi.IOrderDishPersistencePort;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class OrderDishUseCase implements IOrderDishServicePort {
    private final IOrderDishPersistencePort orderDishPersistencePort;
    private final IOrderRepository orderRepository;

    private final IDishRepository dishRepository;




    public OrderDishUseCase(IOrderDishPersistencePort orderDishPersistencePort, IOrderRepository orderRepository, IDishRepository dishRepository) {
        this.orderDishPersistencePort = orderDishPersistencePort;
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
    }

    @Override
    public void saveOrderDish(OrderDish orderDish) {
        Long idOrder= orderDish.getOrder().getId();
        List idRestaurant = orderRepository.findRestaurantById(idOrder);
        Iterator<OrderEntity> it = idRestaurant.iterator();
        Long restaurantId = it.next().getRestaurantEntity().getId();

        List<DishEntity> dishes = dishRepository.findAllByRestaurantEntityId(restaurantId);

        Optional<OrderEntity> orderEntity = orderRepository.findById(idOrder);

        if(idRestaurant.isEmpty()){
            throw  new OrderNotFoundException();
        }

        if(dishes.isEmpty()){
           throw new DishNotFoundInRestaurantException();
        }

        orderDishPersistencePort.saveOrderDish(orderDish);
    }
}
