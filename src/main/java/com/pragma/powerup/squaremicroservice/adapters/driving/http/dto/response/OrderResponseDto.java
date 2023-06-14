package com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class OrderResponseDto {
    private Long idClient;
    private LocalDate dateOrder;
    private String status;
    private Long idChef;
    private Long idRestaurant;
}
