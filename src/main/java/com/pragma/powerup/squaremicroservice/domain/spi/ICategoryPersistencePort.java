package com.pragma.powerup.squaremicroservice.domain.spi;


import com.pragma.powerup.squaremicroservice.domain.model.Category;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);

}
