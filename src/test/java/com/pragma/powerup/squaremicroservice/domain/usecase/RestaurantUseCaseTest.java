package com.pragma.powerup.squaremicroservice.domain.usecase;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IEmployeeRepository;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
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
import com.pragma.powerup.squaremicroservice.domain.spi.IEmployeeHttpAdapterPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.spi.IEmployeePersistencePort;
import com.pragma.powerup.squaremicroservice.domain.spi.IOwnerHttpAdapterPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
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

    @Mock
    private IEmployeeHttpAdapterPersistencePort employeeHttpAdapterPersistencePort;

    @Mock
    private IOwnerHttpAdapterPersistencePort ownerHttpAdapterPersistencePort;

    @Mock
    private RestTemplate restTemplate;

    private RestaurantUseCase restaurantUseCase;

    @BeforeEach
    public void setUp() {
        restaurantUseCase = new RestaurantUseCase(restaurantPersistencePort,employeePersistencePort, restaurantRepository, employeeHttpAdapterPersistencePort, ownerHttpAdapterPersistencePort);
        restTemplate = new RestTemplate();
    }

    @Test
    void saveRestaurantValidUser() {
        // Arrange
        Long ownerId = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setIdOwner(ownerId);

        User user = new User();
        user.setIdRole(Constants.OWNER_ROLE_ID);

        when(ownerHttpAdapterPersistencePort.getOwner(ownerId)).thenReturn(user);

        // Act
        restaurantUseCase.saveRestaurant(restaurant);

        // Assert
        verify(ownerHttpAdapterPersistencePort, times(1)).getOwner(ownerId);
        verify(restaurantPersistencePort, times(1)).saveRestaurant(restaurant);

    }

    @Test
    void saveRestaurantInvalidUser() {
        // Arrange
        Long ownerId = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setIdOwner(ownerId);

        User user = new User();
        user.setIdRole(6L);

        when(ownerHttpAdapterPersistencePort.getOwner(ownerId)).thenReturn(user);

        // Act & Assert
        Assertions.assertThrows(UserNotBeAOwnerException.class, () -> {
            restaurantUseCase.saveRestaurant(restaurant);
        });

        verify(ownerHttpAdapterPersistencePort, times(1)).getOwner(ownerId);
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
        Long idEmployee = 52L;
        Long idOwnerToken = 123456789L;

        Interceptor.setIdUser(idOwnerToken);

        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto(52L, 123L);

        User user = new User();
        user.setId(idEmployee);
        user.setDniNumber("12345678");
        user.setIdRole(Constants.EMPLOYEE_ROLE_ID);

        RestaurantEntity restaurantEntity = new RestaurantEntity(123L, "Alitas BBQ", "carrera 13 #12-12",
                "456789",
                "imagen.jpg",
                idOwnerToken, "123456");

        Employee employee = new Employee(6L, 52L,"123456789",123L);


        when(restaurantRepository.findById(restaurantEntity.getId())).thenReturn(Optional.of(restaurantEntity));
        when(employeeHttpAdapterPersistencePort.getEmployee(employeeRequestDto.getIdEmployee())).thenReturn(user);
        doNothing().when(employeePersistencePort).addEmployee(employee);

        // Act
        restaurantUseCase.addEmployee(employee);


        verify(employeeHttpAdapterPersistencePort, times(1)).getEmployee(idEmployee);
        verify(employeePersistencePort, times(1)).addEmployee(employee);


    }



}