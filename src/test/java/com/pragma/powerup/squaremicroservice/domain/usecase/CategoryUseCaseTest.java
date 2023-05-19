package com.pragma.powerup.squaremicroservice.domain.usecase;

import com.pragma.powerup.squaremicroservice.domain.exceptions.UserNotBeAOwnerException;
import com.pragma.powerup.squaremicroservice.domain.model.Category;
import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.squaremicroservice.domain.model.User;
import com.pragma.powerup.squaremicroservice.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSaveCategory() {
        // Arrange
        Category category = new Category(50L, "Pasta", "Aquí va la cómida italiana");

        categoryPersistencePort.saveCategory(category);

        // Act
        categoryUseCase.saveCategory(category);

        // Assert
        Mockito.verify(categoryPersistencePort, times(1)).saveCategory(category);
    }

}