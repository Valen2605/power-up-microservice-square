package com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class DishOrderRequestDto {
    @NotEmpty
    private String dishType;
    @JsonIgnore
    private Integer priority;
    @Min(value = 250, message = "The value should not be less than 250 grams.")
    @Max(value = 750, message = "The value should not be greater than 750 grams.")
    private Integer grams;
    private String companion;
    private String dessertType;
    private String complement;
    private String flavor;

}
