package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.CategoryAlreadyExistsException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.mappers.ICategoryEntityMapper;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.ICategoryRepository;
import com.pragma.powerup.squaremicroservice.domain.model.Category;
import com.pragma.powerup.squaremicroservice.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
public class CategoryMysqlAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public void saveCategory(Category category) {

        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new CategoryAlreadyExistsException();
        }
        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }

}
