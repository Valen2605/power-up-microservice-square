package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.mappers;


import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.pragma.powerup.squaremicroservice.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryEntityMapper {
    CategoryEntity toEntity(Category category);
}
