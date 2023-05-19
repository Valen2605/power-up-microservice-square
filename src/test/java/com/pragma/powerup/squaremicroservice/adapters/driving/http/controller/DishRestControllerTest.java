package com.pragma.powerup.squaremicroservice.adapters.driving.http.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.IDishHandler;
import com.pragma.powerup.squaremicroservice.configuration.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DishRestControllerTest {

    @Mock
    private IDishHandler dishHandler;

    @InjectMocks
    private DishRestController dishRestController;

    private DishRequestDto dishRequestDto;

    @BeforeEach
    void dish() {
        DishRequestDto dishRDto = new DishRequestDto("Arroz Chino", 3L, "Arroz con camarones y raíces chinas", 45000, 2L, "arroz.jpg/arroces.com.co");
    }

    @Test
    void testSaveDish() {
        // Arrange
        Mockito.doNothing().when(dishHandler).saveDish(dishRequestDto);

        // Act
        ResponseEntity<Map<String, String>> responseEntity = dishRestController.saveDish(dishRequestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(Constants.DISH_CREATED_MESSAGE, responseEntity.getBody().get(Constants.RESPONSE_MESSAGE_KEY));
    }

}