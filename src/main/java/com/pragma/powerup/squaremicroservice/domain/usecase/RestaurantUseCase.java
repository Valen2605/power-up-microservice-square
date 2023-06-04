package com.pragma.powerup.squaremicroservice.domain.usecase;


import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.adapter.EmployeeHttpAdapter;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.adapter.OwnerHttpAdapter;
import com.pragma.powerup.squaremicroservice.configuration.Constants;
import com.pragma.powerup.squaremicroservice.configuration.security.Interceptor;
import com.pragma.powerup.squaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.squaremicroservice.domain.exceptions.PageNotFoundException;
import com.pragma.powerup.squaremicroservice.domain.exceptions.UserNotBeAEmployeeException;
import com.pragma.powerup.squaremicroservice.domain.exceptions.UserNotBeAOwnerException;
import com.pragma.powerup.squaremicroservice.domain.model.Employee;
import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.squaremicroservice.domain.model.User;
import com.pragma.powerup.squaremicroservice.domain.spi.IEmployeePersistencePort;
import com.pragma.powerup.squaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.utility.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;

    private final IEmployeePersistencePort employeePersistencePort;

    private final IRestaurantRepository restaurantRepository;

    @Autowired
    private OwnerHttpAdapter ownerHttpAdapter;

    @Autowired
    private EmployeeHttpAdapter employeeHttpAdapter;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IEmployeePersistencePort employeePersistencePort, IRestaurantRepository restaurantRepository) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.employeePersistencePort = employeePersistencePort;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant){
        Long idOwner = restaurant.getIdOwner();
        User user = ownerHttpAdapter.getOwner(idOwner);
        if (!user.getIdRole().equals(Constants.OWNER_ROLE_ID)){
            throw new UserNotBeAOwnerException();
        }
        restaurantPersistencePort.saveRestaurant(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants(int page, int pageSize) {
        if (page>0){
            List<Restaurant> restaurants = restaurantPersistencePort.getAllRestaurants(page, pageSize);
            restaurants.sort(Comparator.comparing(Restaurant::getName));
            return  Pagination.paginate(restaurants, pageSize, page);
        }
        throw new PageNotFoundException();
    }

    public void validateOwner(Long idRestaurant){
        if(!restaurantRepository.findById(idRestaurant).isPresent()){
            throw new RestaurantNotFoundException();
        }
        Optional<RestaurantEntity> restaurant = restaurantRepository.findById(idRestaurant);
        Long userId = Interceptor.getIdUser();
        if (!restaurant.get().getIdOwner().equals(userId)) {
            throw new UserNotBeAOwnerException();
        }
    }

    @Override
    public void addEmployee(Employee employee) {
        validateOwner(employee.getIdRestaurant());
        Long idEmployee = employee.getIdEmployee();
        User user = employeeHttpAdapter.getEmployee(idEmployee);
        employee.setDniNumber(user.getDniNumber());
        if(!user.getIdRole().equals(Constants.EMPLOYEE_ROLE_ID)){
            throw new UserNotBeAEmployeeException();
        }
        employeePersistencePort.addEmployee(employee);
    }


}
