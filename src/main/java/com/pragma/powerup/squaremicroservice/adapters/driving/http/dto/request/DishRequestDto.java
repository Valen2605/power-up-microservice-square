package com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DishRequestDto {

    @NotEmpty(message = "Name may not be empty")
    @Pattern(regexp = "^(?=.*[a-zA-Z\s])[a-zA-Z\s0-9]+$", message = "The name is not valid")
    private String name;

    @NotNull(message = "The category must not be empty")
    private Long idCategory;

    @NotEmpty(message = "Description may not be empty")
    private String description;

    @Positive(message = "Price must be a positive value")
    @Min(value = 1, message = "Price must be greater than 0")
    private Integer price;

    @NotNull(message = "The restaurant must not be empty")
    private Long idRestaurant;

    @NotEmpty(message = "url image may not be empty")
    private String urlImage;
}
