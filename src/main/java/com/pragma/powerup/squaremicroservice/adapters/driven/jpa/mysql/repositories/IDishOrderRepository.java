package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.DishOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface IDishOrderRepository extends JpaRepository<DishOrderEntity, Long> {

    @Query("SELECT d FROM DishOrderEntity d order by d.priority ASC ")
    List<DishOrderEntity> findAll();

}
