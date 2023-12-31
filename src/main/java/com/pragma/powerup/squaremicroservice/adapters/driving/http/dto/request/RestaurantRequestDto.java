package com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RestaurantRequestDto {
    @NotEmpty(message = "Name may not be empty")
    @Pattern(regexp = "^(?=.*[a-zA-Z\s])[a-zA-Z\s0-9]+$", message = "The name is not valid")
    private String name;
    @NotEmpty(message = "Address may not be empty")
    private String address;
    @NotEmpty(message = "Phone may not be empty")
    @Pattern(regexp = "^\\+?[0-9]{1,12}$", message = "The phone is not valid")
    private String phone;
    @NotEmpty(message = "UrlLogo may not be empty")
    private String urlLogo;
    @NotNull(message = "IdOwner may not be empty")
    private Long idOwner;
    @NotEmpty(message = "DniNumber may not be empty")
    @Pattern(regexp = "^[0-9]{1,20}$", message = "The dniNumber is not valid")
    private String dniNumber;
}
