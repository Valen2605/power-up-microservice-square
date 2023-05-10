package com.pragma.powerup.squaremicroservice.adapters.driving.http.controller;


import com.pragma.powerup.squaremicroservice.adapters.driving.http.adapter.OwnerHttpAdapter;
import com.pragma.powerup.squaremicroservice.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class OwnerRestControllerTest {

    @Mock
    private RestTemplate restTemplate;

    private OwnerHttpAdapter ownerHttpAdapter;

    @BeforeEach
    public void owner() {
        ownerHttpAdapter = new OwnerHttpAdapter(restTemplate);
    }

    @Test
    public void testCreateRestaurant() {
        // Arrange
        Long id = 13L;
        String url = "http://localhost:8090/user/" + id;
        User expectedUser = new User();
        expectedUser.setId(13L);
        expectedUser.setName("Valentina");
        expectedUser.setIdRole(2L);

        Mockito.when(restTemplate.getForObject(url, User.class)).thenReturn(expectedUser);

        // Act
        User result = ownerHttpAdapter.getOwner(id);

        // Assert
        assertEquals(expectedUser, result);
        Mockito.verify(restTemplate, times(1)).getForObject(url, User.class);
    }
}