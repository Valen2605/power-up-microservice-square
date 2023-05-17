package com.pragma.powerup.squaremicroservice.domain.usecase;

import com.pragma.powerup.squaremicroservice.domain.api.ICategoryServicePort;
import com.pragma.powerup.squaremicroservice.domain.model.Category;
import com.pragma.powerup.squaremicroservice.domain.spi.ICategoryPersistencePort;



public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveCategory(Category category){

        categoryPersistencePort.saveCategory(category);
    }

}
