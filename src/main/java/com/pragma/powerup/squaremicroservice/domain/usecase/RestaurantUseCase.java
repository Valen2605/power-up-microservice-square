package com.pragma.powerup.squaremicroservice.domain.usecase;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.adapter.OwnerHttpAdapter;
import com.pragma.powerup.squaremicroservice.configuration.Constants;
import com.pragma.powerup.squaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.squaremicroservice.domain.exceptions.UserNotBeAOwnerException;
import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.squaremicroservice.domain.model.User;
import com.pragma.powerup.squaremicroservice.domain.spi.IRestaurantPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;

    private String message;

    @Autowired
    private OwnerHttpAdapter ownerHttpAdapter;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant){
        Long idOwner = restaurant.getIdOwner();
        User user = ownerHttpAdapter.getOwner(idOwner);

        /*if (!user.getIdRole().equals(null)){
            throw new UserNotBeAOwnerException();
        }*/

        if (!user.getIdRole().equals (Constants.OWNER_ROLE_ID)){
            throw new UserNotBeAOwnerException();
        }

        restaurantPersistencePort.saveRestaurant(restaurant);
    }
}
