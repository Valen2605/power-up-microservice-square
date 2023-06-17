package com.pragma.powerup.squaremicroservice.domain.usecase;


import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.squaremicroservice.domain.api.IOrderDishServicePort;
import com.pragma.powerup.squaremicroservice.domain.model.OrderDish;
import com.pragma.powerup.squaremicroservice.domain.spi.IOrderDishPersistencePort;



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
        orderDishPersistencePort.saveOrderDish(orderDish);
    }
}
