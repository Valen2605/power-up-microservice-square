package com.pragma.powerup.squaremicroservice.domain.usecase;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IEmployeeRepository;
import com.pragma.powerup.squaremicroservice.domain.exceptions.UserNotBeAOwnerException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.adapter.EmployeeHttpAdapter;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.adapter.OwnerHttpAdapter;
import com.pragma.powerup.squaremicroservice.configuration.Constants;
import com.pragma.powerup.squaremicroservice.configuration.security.Interceptor;
import com.pragma.powerup.squaremicroservice.domain.exceptions.PageNotFoundException;
import com.pragma.powerup.squaremicroservice.domain.model.Employee;
import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.squaremicroservice.domain.model.User;
import com.pragma.powerup.squaremicroservice.domain.spi.IEmployeePersistencePort;
import com.pragma.powerup.squaremicroservice.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private OwnerHttpAdapter ownerHttpAdapter;
    @Mock
    private IRestaurantRepository restaurantRepository;

    @Mock
    private IEmployeeRepository employeeRepository;
    @Mock
    private EmployeeHttpAdapter employeeHttpAdapter;

    @Mock
    private IEmployeePersistencePort employeePersistencePort;



    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        restaurantUseCase = new RestaurantUseCase(restaurantPersistencePort,employeePersistencePort, restaurantRepository);
    }


    @Test
    void getAllRestaurantsValidPage() {
        // Arrange
        int page = 1;
        int pageSize = 3;
        List<Restaurant> restaurants = Arrays.asList(
                new Restaurant(13L, "Mi Restaurante 1", "carrera 13 #12-12","456789","image.jpg",1L, "123456"),
                new Restaurant(14L, "Mi Restaurante 2", "carrera 13 #12-12","456789","image.jpg",1L, "123456"),
                new Restaurant(15L, "Mi Restaurante 3", "carrera 13 #12-12","456789","image.jpg",1L, "123456")
        );
        when(restaurantPersistencePort.getAllRestaurants(page, pageSize)).thenReturn(restaurants);

        // Act
        List<Restaurant> result = restaurantPersistencePort.getAllRestaurants(page, pageSize);

        // Assert
        verify(restaurantPersistencePort, times(1)).getAllRestaurants(page, pageSize);
        assertEquals(pageSize, result.size());
        assertEquals("Mi Restaurante 1", result.get(0).getName());
        assertEquals("Mi Restaurante 2", result.get(1).getName());
        assertEquals("Mi Restaurante 3", result.get(2).getName());
    }

    @Test
    void getAllRestaurantsInvalidPage() {
        // Arrange
        int page = 0;
        int pageSize = 10;

        // Act and Assert
        Assertions.assertThrows(PageNotFoundException.class, () -> {
            restaurantUseCase.getAllRestaurants(page, pageSize);
        });
    }

    @Test
    void validateOwnerWhenRestaurantNotFound() {
        // Arrange
        Long idRestaurant = 1L;
        when(restaurantRepository.findById(idRestaurant)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RestaurantNotFoundException.class, () -> restaurantUseCase.validateOwner(idRestaurant));

        // Verify
        verify(restaurantRepository).findById(idRestaurant);
    }

    @Test
    void validateOwnerWhenRestaurantValid() {
        // Arrange
        Long idRestaurant = 1L;
        Long idOwnerToken = 123456789L;

        Interceptor.setIdUser(idOwnerToken);

        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setIdOwner(119129L);

        when(restaurantRepository.findById(idRestaurant)).thenReturn(Optional.of(restaurantEntity));

        // Act y Assert
        assertThrows(UserNotBeAOwnerException.class, () -> restaurantUseCase.validateOwner(idRestaurant));
    }

    @Test
    void testAddEmployee() {
        // Arrange
        Long idRestaurant = 123L;
        Long idEmployee = 456L;
        Employee employee = new Employee(6L, 456L, "12345678", 123L);

        Long idOwnerToken = 123456789L;

        Interceptor.setIdUser(idOwnerToken);

        User user = new User();
        user.setDniNumber("12345678");
        user.setIdRole(Constants.EMPLOYEE_ROLE_ID);

        Restaurant restaurant = new Restaurant(123L, "Alitas BBQ", "carrera 13 #12-12",
                "456789",
                "imagen.jpg",
                123456789L, "123456");

        RestaurantEntity restaurantEntity = new RestaurantEntity(123L, "Alitas BBQ", "carrera 13 #12-12",
                "456789",
                "imagen.jpg",
                123456789L, "123456");

        when(employeeHttpAdapter.getEmployee(idEmployee)).thenReturn(user);
        when(restaurantRepository.findById(restaurantEntity.getId())).thenReturn(Optional.of(restaurantEntity));
        employeePersistencePort.addEmployee(employee);


        // Act
        restaurantUseCase.addEmployee(employee);

        // Assert
        verify(employeeHttpAdapter, times(1)).getEmployee(idEmployee);
        verify(employeePersistencePort, times(2)).addEmployee(employee);

    }



}