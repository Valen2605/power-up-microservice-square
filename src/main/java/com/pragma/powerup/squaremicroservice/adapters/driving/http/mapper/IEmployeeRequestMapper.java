package com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.squaremicroservice.domain.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IEmployeeRequestMapper {
    @Mapping(target = "idRestaurant", source="idRestaurant")
    Employee toEmployee(EmployeeRequestDto employeeRequestDto);

}
