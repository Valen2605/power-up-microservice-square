package com.pragma.powerup.squaremicroservice.domain.api;

import com.pragma.powerup.squaremicroservice.domain.model.Category;

public interface ICategoryServicePort {
    void saveCategory(Category category);
}
