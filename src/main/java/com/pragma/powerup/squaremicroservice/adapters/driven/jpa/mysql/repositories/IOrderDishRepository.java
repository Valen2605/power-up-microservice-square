package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories;



import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface IOrderDishRepository extends JpaRepository<OrderDishEntity, Long> {

}
