package com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper;


import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response.EmployeeResponseDto;
import com.pragma.powerup.squaremicroservice.domain.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IEmployeeResponseMapper {

    @Mapping(target = "idRestaurant", source = "idRestaurant")
    EmployeeResponseDto toResponse(Employee employee);
    List<EmployeeResponseDto> toResponseList(List<Employee> employeeList);
}
