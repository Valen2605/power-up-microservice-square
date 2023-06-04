package com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmployeeResponseDto {

    private Long idEmployee;
    private String dniNumber;
    private Long idRestaurant;
}
