package com.pragma.powerup.squaremicroservice.domain.spi;

import com.pragma.powerup.squaremicroservice.domain.model.User;
public interface IEmployeeHttpAdapterPersistencePort {

    User getEmployee(Long id);
}
