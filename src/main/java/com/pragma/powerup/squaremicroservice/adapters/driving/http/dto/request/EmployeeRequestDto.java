package com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
