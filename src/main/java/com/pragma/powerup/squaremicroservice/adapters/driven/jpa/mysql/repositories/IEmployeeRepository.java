package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.EmployeeRestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface IEmployeeRepository extends JpaRepository<EmployeeRestaurantEntity, Long> {

    Optional<EmployeeRestaurantEntity> findByDniNumber(String dniNumber);

    List<EmployeeRestaurantEntity> findAllById(Long idEmployee);

}