package com.pragma.powerup.squaremicroservice.configuration;



import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.adapter.CategoryMysqlAdapter;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.adapter.DishMysqlAdapter;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.adapter.EmployeeMysqlAdapter;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.adapter.RestaurantMysqlAdapter;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.mappers.ICategoryEntityMapper;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.mappers.IEmployeeEntityMapper;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.ICategoryRepository;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IEmployeeRepository;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.adapter.EmployeeHttpAdapter;
import com.pragma.powerup.squaremicroservice.domain.api.ICategoryServicePort;
import com.pragma.powerup.squaremicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.squaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.squaremicroservice.domain.spi.ICategoryPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.spi.IEmployeePersistencePort;
import com.pragma.powerup.squaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.usecase.CategoryUseCase;
import com.pragma.powerup.squaremicroservice.domain.usecase.DishUseCase;
import com.pragma.powerup.squaremicroservice.domain.usecase.RestaurantUseCase;
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

    private final EmployeeHttpAdapter employeeHttpAdapter;
    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort(), employeePersistencePort(), restaurantRepository, employeeHttpAdapter);
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
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
