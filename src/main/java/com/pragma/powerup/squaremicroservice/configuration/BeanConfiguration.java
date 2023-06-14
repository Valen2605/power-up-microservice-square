package com.pragma.powerup.squaremicroservice.configuration;



import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.adapter.*;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.mappers.*;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.*;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.adapter.EmployeeHttpAdapter;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.adapter.OwnerHttpAdapter;
import com.pragma.powerup.squaremicroservice.domain.api.*;
import com.pragma.powerup.squaremicroservice.domain.spi.*;
import com.pragma.powerup.squaremicroservice.domain.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    private final IEmployeeRepository employeeRepository;
    private final IEmployeeEntityMapper employeeEntityMapper;

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;

    private final IOrderDishRepository orderDishRepository;
    private final IOrderDishEntityMapper orderDishEntityMapper;


    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort(), employeePersistencePort(),
                                     restaurantRepository, employeeHttpAdapterPersistencePort(),
                                     ownerHttpAdapterPersistencePort());
    }

    @Bean
    public IEmployeeHttpAdapterPersistencePort employeeHttpAdapterPersistencePort() {
        return new EmployeeHttpAdapter();
    }

    @Bean
    public IOwnerHttpAdapterPersistencePort ownerHttpAdapterPersistencePort() {
        return new OwnerHttpAdapter();
    }
    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase(restaurantRepository, dishRepository, dishPersistencePort());
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantMysqlAdapter(restaurantRepository, restaurantEntityMapper);
    }
    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishMysqlAdapter(dishRepository, dishEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }
    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {return new CategoryMysqlAdapter(categoryRepository, categoryEntityMapper);
    }
    @Bean
    public IEmployeePersistencePort employeePersistencePort() {
        return new EmployeeMysqlAdapter(employeeRepository, employeeEntityMapper);
    }

    @Bean
    public IOrderServicePort orderServicePort() {
        return new OrderUseCase(orderRepository, orderPersistencePort(), restaurantPersistencePort());
    }

    @Bean
    public IOrderDishServicePort orderDishServicePort(){
        return new OrderDishUseCase(orderDishPersistencePort(), orderRepository, dishRepository);
    }
    @Bean
    public IOrderPersistencePort orderPersistencePort() {
        return new OrderMysqlAdapter(orderRepository, orderEntityMapper,restaurantRepository);
    }

    @Bean
    public IOrderDishPersistencePort orderDishPersistencePort(){
        return new OrderDishMysqlAdapter(orderDishRepository, orderDishEntityMapper);
    }
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
