package com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderReadyRequestDto {

    @JsonIgnore
    private String status;

    @JsonIgnore
    private Long codeOrder;


}
