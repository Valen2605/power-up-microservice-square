package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories;


import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {


        Optional<OrderEntity> findByIdClient(Long idClient);

        List<OrderEntity> findByRestaurantEntityId(Long idRestaurant);

        boolean existsByIdClient(Long idClient);

        Page<OrderEntity> findAllByStatusAndRestaurantEntityId(String status, Long idRestaurant, Pageable pageable);

}
