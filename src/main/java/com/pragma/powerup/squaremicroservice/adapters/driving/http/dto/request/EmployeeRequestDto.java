package com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmployeeRequestDto {

    @NotNull(message = "IdEmployee may not be empty")
    private Long idEmployee;
    @NotNull(message = "IdRestaurant may not be empty")
    private Long idRestaurant;
}
