package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IDishRepository extends JpaRepository<DishEntity, Long> {
        List<DishEntity> findAll();
        List<DishEntity> findAllByRestaurantEntityId(Long idRestaurant);
        List<DishEntity> findAllByCategoryEntityId(Long idCategory);

        Page<DishEntity> findAllByRestaurantEntityIdAndCategoryEntityId(Long idRestaurantEntity, Long idCategoryEntity, Pageable pageable);

}
