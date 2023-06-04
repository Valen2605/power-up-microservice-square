package com.pragma.powerup.squaremicroservice.domain.spi;

import com.pragma.powerup.squaremicroservice.domain.model.Employee;


public interface IEmployeePersistencePort {
    void addEmployee(Employee employee);
}
