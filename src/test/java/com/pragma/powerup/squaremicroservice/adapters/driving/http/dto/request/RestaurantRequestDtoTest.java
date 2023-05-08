package com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantRequestDtoTest {

    @InjectMocks
    private RestaurantRequestDto restaurantRequestDto;
    @Test
    void testRestaurantDto() {
        // Arrange
        String name = "Name Restaurant";
        String address = "Carrera 13 #12-12";
        String phone = "56789";
        String urlLogo = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com.mx%2Fhigueron969%2Falitas-1%2F&psig=AOvVaw2MQshKE0eZLnHhhxf9jCd5&ust=1683648032206000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCMjZwPGt5v4CFQAAAAAdAAAAABAX";
        Long idOwner = 1L;
        String dniNumber = "1234567";

        // Act
        RestaurantRequestDto restaurant = new RestaurantRequestDto(name, address, phone, urlLogo, idOwner, dniNumber);

        // Assert
        assertEquals(name, restaurant.getName());
        assertEquals(address, restaurant.getAddress());
        assertEquals(phone, restaurant.getPhone());
        assertEquals(urlLogo, restaurant.getUrlLogo());
        assertEquals(idOwner, restaurant.getIdOwner());
        assertEquals(dniNumber, restaurant.getDniNumber());
    }

}