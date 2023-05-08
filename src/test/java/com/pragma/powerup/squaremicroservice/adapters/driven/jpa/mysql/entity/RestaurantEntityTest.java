package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantEntityTest {
    @Test
    void testGetters() {
        //Arrange
        RestaurantEntity restaurantEntity = new RestaurantEntity(13L,"Alitas BBQ",
                "Carrera 13 #12-12","456789",
                "https://i.pinimg.com/550x/15/53/df/1553dfba785bdafc6131c0792f537670.jpg",
                2L, "123456");

        // Act & Assert
        assertEquals(13L, restaurantEntity.getId());
        assertEquals("Alitas BBQ", restaurantEntity.getName());
        assertEquals("Carrera 13 #12-12", restaurantEntity.getAddress());
        assertEquals("456789", restaurantEntity.getPhone());
        assertEquals("https://i.pinimg.com/550x/15/53/df/1553dfba785bdafc6131c0792f537670.jpg", restaurantEntity.getUrlLogo());
        assertEquals(2L, restaurantEntity.getIdOwner());
        assertEquals("123456", restaurantEntity.getDniNumber());
    }

    @Test
    void testSetters() {
        //Arrange
        RestaurantEntity restaurantEntity = new RestaurantEntity();

        //Act
        restaurantEntity.setId(13L);
        restaurantEntity.setName("Alitas BBQ");
        restaurantEntity.setAddress("Carrera 13 #12-12");
        restaurantEntity.setPhone("456789");
        restaurantEntity.setUrlLogo("https://i.pinimg.com/550x/15/53/df/1553dfba785bdafc6131c0792f537670.jpg");
        restaurantEntity.setIdOwner(2L);
        restaurantEntity.setDniNumber("123456");

        //Assert
        assertEquals(13L, restaurantEntity.getId());
        assertEquals("Alitas BBQ", restaurantEntity.getName());
        assertEquals("Carrera 13 #12-12", restaurantEntity.getAddress());
        assertEquals("456789", restaurantEntity.getPhone());
        assertEquals("https://i.pinimg.com/550x/15/53/df/1553dfba785bdafc6131c0792f537670.jpg", restaurantEntity.getUrlLogo());
        assertEquals(2L, restaurantEntity.getIdOwner());
        assertEquals("123456", restaurantEntity.getDniNumber());
    }

}