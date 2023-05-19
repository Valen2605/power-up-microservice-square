package com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DishUpdateRequestDto {

    @NotEmpty(message = "Description may not be empty")
    private String description;

    @Positive(message = "Price must be a positive value")
    @Min(value = 1, message = "Price must be greater than 0")
    private Integer price;


}
