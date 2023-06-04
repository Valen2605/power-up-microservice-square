package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.EmployeeRestaurantEntity;
import com.pragma.powerup.squaremicroservice.domain.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IEmployeeEntityMapper {

    @Mapping(target = "restaurantEntity.id", source = "idRestaurant")
    EmployeeRestaurantEntity toEntity(Employee employee);

    List<Employee> toEmployeeList(List<EmployeeRestaurantEntity> employeeEntityList);

}
