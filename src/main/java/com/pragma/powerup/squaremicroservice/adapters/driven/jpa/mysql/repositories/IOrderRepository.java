package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories;


import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
        Optional<OrderEntity> findClientById(Long clientId);

        List<OrderEntity> findRestaurantById(Long idOrder);

        @Query("SELECT o FROM OrderEntity o WHERE o.idClient = ?1 OR o.status = ?2")
        List<OrderEntity> findClientByIdAndStatus(Long idClient, String status);

}
