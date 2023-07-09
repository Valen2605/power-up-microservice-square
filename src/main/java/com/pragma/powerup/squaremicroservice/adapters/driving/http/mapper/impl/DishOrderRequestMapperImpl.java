package com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper.impl;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishOrderRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper.IDishOrderRequestMapper;
import com.pragma.powerup.squaremicroservice.domain.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DishOrderRequestMapperImpl implements IDishOrderRequestMapper {

    @Override
    public DishOrder toDishOrder(DishOrderRequestDto dishOrderRequestDto) {
        if (dishOrderRequestDto == null) {
            return null;
        }

        String dishType = dishOrderRequestDto.getDishType();
        DishOrder dishOrder;

         if ("carne".equalsIgnoreCase(dishType)) {
            Meat meat = new Meat();
            meat.setTypeDish(dishType);
            meat.setGrams(dishOrderRequestDto.getGrams());
            return meat;
        } else if ("sopa".equalsIgnoreCase(dishType)) {
            Soup soup = new Soup();
            soup.setTypeDish(dishType);
            soup.setCompanion(dishOrderRequestDto.getCompanion());
            return soup;
        } else if ("postre".equalsIgnoreCase(dishType)) {
             dishOrder = saveDessert(dishOrderRequestDto);
        } else {
            throw new IllegalArgumentException("Dish not exist: ");
        }
        return dishOrder;
    }

    @Override
    public List<DishOrder> toDishesOrder(List<DishOrderRequestDto> ordersRequestDto) {
        List<DishOrder> dishesOrder = new ArrayList<>();
        for (DishOrderRequestDto dishOrderRequestDto : ordersRequestDto) {
            DishOrder dishOrder = toDishOrder(dishOrderRequestDto);
            dishesOrder.add(dishOrder);
        }
        return dishesOrder;
    }

    private Dessert saveDessert(DishOrderRequestDto dishOrderRequestDto) {
        String dessertType = dishOrderRequestDto.getDessertType();
        Dessert dessert;

        if ("flan".equalsIgnoreCase(dessertType)) {
            dessert = new Flan();
            Flan flan = (Flan) dessert;
            flan.setTypeDish(dishOrderRequestDto.getDishType());
            flan.setTypeDessert(dishOrderRequestDto.getDessertType());
            flan.setComplement(dishOrderRequestDto.getComplement());
        } else if ("helado".equalsIgnoreCase(dessertType)) {
            dessert = new IceCream();
            IceCream iceCream = (IceCream) dessert;
            iceCream.setTypeDish(dishOrderRequestDto.getDishType());
            iceCream.setTypeDessert(dishOrderRequestDto.getDessertType());
            iceCream.setFlavor(dishOrderRequestDto.getFlavor());
        } else {
            throw new IllegalArgumentException("Dessert not found: " + dessertType);
        }
        return dessert;
    }


}
