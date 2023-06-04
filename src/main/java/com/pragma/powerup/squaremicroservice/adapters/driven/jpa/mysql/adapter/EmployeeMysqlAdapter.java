package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.adapter;



import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.EmployeeAlreadyExistsException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantAlreadyExistsException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.mappers.IEmployeeEntityMapper;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IEmployeeRepository;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.squaremicroservice.domain.model.Employee;
import com.pragma.powerup.squaremicroservice.domain.spi.IEmployeePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;



@RequiredArgsConstructor
@Transactional
public class EmployeeMysqlAdapter implements IEmployeePersistencePort {

    private final IEmployeeRepository employeeRepository;
    private final IEmployeeEntityMapper employeeEntityMapper;


    @Override
    public void addEmployee(Employee employee) {

        if (employeeRepository.findByDniNumber(employee.getDniNumber()).isPresent()) {
            throw new EmployeeAlreadyExistsException();
        }
          employeeRepository.save(employeeEntityMapper.toEntity(employee));
    }
}
