package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IRestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    Optional<RestaurantEntity> findByDniNumber(String dniNumber);

    List<RestaurantEntity> findAllById(Long idUser);

}