package com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DishOrderResponseDto {

    private String typeDish;
    private Integer grams;
    private String companion;
    private String dessertType;
    private String complement;
    private String flavor;
    private Integer priority;
}
