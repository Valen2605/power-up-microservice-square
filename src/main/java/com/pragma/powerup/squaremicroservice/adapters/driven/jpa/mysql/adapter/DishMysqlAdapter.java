package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.adapter;


import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.CategoryNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.DishAlreadyExistsException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.squaremicroservice.domain.model.Dish;
import com.pragma.powerup.squaremicroservice.domain.spi.IDishPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
public class DishMysqlAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;

    @Override
    public void saveDish(Dish dish) {
        List<DishEntity> dishes = dishRepository.findAllByRestaurantEntityId(dish.getRestaurant().getId());
        dishes.forEach(d -> {
            if(d.getName().equals(dish.getName())){
                throw new DishAlreadyExistsException();
            }
        });

        if (dishRepository.findAllByRestaurantEntityId(dish.getRestaurant().getId()).isEmpty()){
            throw new RestaurantNotFoundException();
        }

        if (dishRepository.findAllByCategoryEntityId(dish.getCategory().getId()).isEmpty()){
            throw new CategoryNotFoundException();
        }

        dishRepository.save(dishEntityMapper.toEntity(dish));

    }

    @Override
    public void updateDish(Long id, Dish dish){

        DishEntity dishEntityUpdate = dishRepository.findById(id).orElseThrow(DishNotFoundException::new);

        dishEntityUpdate.setDescription(dish.getDescription());
        dishEntityUpdate.setPrice(dish.getPrice());
        dishRepository.save(dishEntityUpdate);

    }

    @Override
    public void enableDisableDish(Long id){
        DishEntity dish = dishRepository.findById(id).orElseThrow(DishNotFoundException::new);
        dish.setActive(!dish.getActive());
            dishRepository.save(dish);
    }

    @Override
    public List<Dish> getDishes(Long idRestaurant, Long idCategory,int page, int pageSize) {

        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by("name").ascending());
        Page<DishEntity> dishPage;
        if(idCategory == 0L) {
            dishPage = dishRepository.findAllByRestaurantEntityIdAndCategoryEntityId(idRestaurant,idCategory,pageRequest);
            return dishEntityMapper.toDishList(dishPage.getContent());
        }
        if(idCategory > 0L){
            dishPage = dishRepository.findAllByRestaurantEntityIdAndCategoryEntityId(idRestaurant, idCategory, pageRequest);
            return dishEntityMapper.toDishList(dishPage.getContent());
        }
        throw new DishNotFoundException();
    }
}

