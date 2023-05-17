package com.pragma.powerup.squaremicroservice.adapters.driving.http.mapper;


import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.CategoryRequestDto;
import com.pragma.powerup.squaremicroservice.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryRequestMapper {
    Category toCategory(CategoryRequestDto categoryRequestDto);
}
