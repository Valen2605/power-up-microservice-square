package com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderRequestDto {

    @NotNull(message = "IdRestaurant may not be empty")
    private Long idRestaurant;

    @JsonIgnore
    private String codeOrder;
}
