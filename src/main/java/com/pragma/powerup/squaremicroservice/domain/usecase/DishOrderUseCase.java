package com.pragma.powerup.squaremicroservice.domain.usecase;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.DishOrderEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.OrderNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IDishOrderRepository;
import com.pragma.powerup.squaremicroservice.domain.api.IDishOrderServicePort;
import com.pragma.powerup.squaremicroservice.domain.model.*;
import com.pragma.powerup.squaremicroservice.domain.spi.IDishOrderPersistencePort;

import java.util.List;

public class DishOrderUseCase implements IDishOrderServicePort {

    private final IDishOrderPersistencePort dishOrderPersistencePort;

    private final IDishOrderRepository dishOrderRepository;

    public DishOrderUseCase(IDishOrderPersistencePort dishOrderPersistencePort, IDishOrderRepository dishOrderRepository) {
        this.dishOrderPersistencePort = dishOrderPersistencePort;
        this.dishOrderRepository = dishOrderRepository;
    }


    @Override
    public void saveDishOrder(DishOrder dishOrder) {

        if (dishOrder instanceof Meat) {
            Integer grams = ((Meat) dishOrder).getGrams();
            if(grams.equals(750)){
                dishOrder.setPriority(1);
            }
            if(grams < 750){
                dishOrder.setPriority(2);
            }
        }

        if(dishOrder instanceof Soup){
            String companion = ((Soup) dishOrder).getCompanion();
            if(companion.equalsIgnoreCase("yuca")){
                dishOrder.setPriority(3);
            }
            if(companion.equalsIgnoreCase("papa")){
                dishOrder.setPriority(4);
            }

            if(companion.equalsIgnoreCase("arroz")){
                dishOrder.setPriority(5);
            }
        }

        if(dishOrder instanceof Dessert){
            String dessert = ((Dessert) dishOrder).getTypeDessert();
            if(dessert.equalsIgnoreCase("flan")){
                dishOrder.setPriority(6);
            }
            if(dessert.equalsIgnoreCase("helado")){
                dishOrder.setPriority(7);
            }
        }


        dishOrderPersistencePort.saveDishOrder(dishOrder);

    }

    @Override
    public void saveDishOrders(List<DishOrder> dishList) {
        for (DishOrder dishOrder : dishList) {
            saveDishOrder(dishOrder);
        }
    }

    @Override
    public List<DishOrder> getOrders() {
        List<DishOrder> orders = dishOrderPersistencePort.getOrders();

        List<DishOrderEntity> dishOrderEntities = dishOrderRepository.findAll();
        DishOrderEntity priority = dishOrderEntities.get(0);
        Integer majorPriority = 0;

        if(priority.getPriority() > majorPriority){
            deleteDishOrder(priority.getId());
        }

        if (dishOrderEntities.isEmpty()) {
            throw new OrderNotFoundException();
        }
        return orders;

    }


    @Override
    public void deleteDishOrder(Long id) {
        dishOrderRepository.deleteById(id);
    }

    @Override
    public List<DishOrder> pendingOrders() {

        List<DishOrder> orders = dishOrderPersistencePort.pendingOrders();

        return orders;
    }

}
