package com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DishRequestDto {

    @NotEmpty(message = "Name may not be empty")
    @Pattern(regexp = "^(?=.*[a-zA-Z\s])[a-zA-Z\s0-9]+$", message = "The name is not valid")
    private String name;
    @NotNull(message = "IdCategory may not be empty")
    private Long idCategory;
    @NotEmpty(message = "Description may not be empty")
    private String description;
    @NotNull(message = "Price may not be empty")
    private Integer price;
    @NotNull(message = "IdRestaurant may not be empty")
    private Long idRestaurant;
    @NotNull(message = "IdOwner may not be empty")
    private String urlImage;
    @NotNull(message = "IdOwner may not be empty")
    private Boolean active;
}
