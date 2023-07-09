package com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class DishOrdersRequestDto {

   List<DishOrderRequestDto> dishOrders;

}
