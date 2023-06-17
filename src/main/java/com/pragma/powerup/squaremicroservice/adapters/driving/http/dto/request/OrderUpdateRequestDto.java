package com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderUpdateRequestDto {

    @NotNull(message = "IdChef may not be empty")
    private Long idChef;
    @JsonIgnore
    private String status;


}
