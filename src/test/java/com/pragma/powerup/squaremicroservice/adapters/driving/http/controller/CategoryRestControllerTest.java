package com.pragma.powerup.squaremicroservice.adapters.driving.http.controller;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.CategoryRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.ICategoryHandler;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.IRestaurantHandler;
import com.pragma.powerup.squaremicroservice.configuration.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class CategoryRestControllerTest {


    @Mock
    private ICategoryHandler categoryHandler;

    @InjectMocks
    private CategoryRestController categoryRestController;

    private CategoryRequestDto categoryRequestDto;

    @BeforeEach
    void category() {
        CategoryRequestDto dto = new CategoryRequestDto("Papás", "Deliciosas papas a la francesa");
    }

    @Test
    void testSaveCategory() {
        // Arrange
        Mockito.doNothing().when(categoryHandler).saveCategory(categoryRequestDto);

        // Act
        ResponseEntity<Map<String, String>> responseEntity = categoryRestController.saveCategory(categoryRequestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(Constants.CATEGORY_CREATED_MESSAGE, responseEntity.getBody().get(Constants.RESPONSE_MESSAGE_KEY));
    }

}