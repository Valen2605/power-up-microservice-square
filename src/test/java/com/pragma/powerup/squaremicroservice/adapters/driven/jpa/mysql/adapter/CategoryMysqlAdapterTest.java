package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.CategoryAlreadyExistsException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.mappers.ICategoryEntityMapper;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.ICategoryRepository;
import com.pragma.powerup.squaremicroservice.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CategoryMysqlAdapterTest {

    private CategoryMysqlAdapter categoryMysqlAdapter;

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private ICategoryEntityMapper categoryEntityMapper;

    @BeforeEach
    void category() {
        MockitoAnnotations.openMocks(this);
        categoryMysqlAdapter = new CategoryMysqlAdapter(categoryRepository, categoryEntityMapper);
    }

    @Test
    void saveCategoryIfCategoryNoExist() {
        // Arrange
        Category category = new Category();
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.empty());
        when(categoryEntityMapper.toEntity(category)).thenReturn(new CategoryEntity());

        // Act
        categoryMysqlAdapter.saveCategory(category);

        // Assert
        verify(categoryRepository).findByName(category.getName());
        verify(categoryRepository).save(any(CategoryEntity.class));
    }

    @Test
    void saveCategoryIfCategoryExist() {
        // Arrange
        Category category = new Category();
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(new CategoryEntity()));

        // Act & Assert
        assertThrows(CategoryAlreadyExistsException.class, () -> categoryMysqlAdapter.saveCategory(category));
        verify(categoryRepository).findByName(category.getName());
        verifyNoInteractions(categoryEntityMapper);
        verifyNoMoreInteractions(categoryRepository);
    }

}