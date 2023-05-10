package com.pragma.powerup.squaremicroservice.domain.usecase;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.adapter.OwnerHttpAdapter;
import com.pragma.powerup.squaremicroservice.configuration.Constants;
import com.pragma.powerup.squaremicroservice.domain.exceptions.UserNotBeAOwnerException;
import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.squaremicroservice.domain.model.User;
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

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private OwnerHttpAdapter ownerHttpAdapter;
    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveRestaurant() {
        // Arrange
        User user = new User(13L, "Valentina", "Santa", "123456", "123456789",
                LocalDate.of(1987, 5, 26), "valentina@email.com", "3456", Constants.OWNER_ROLE_ID);


        Restaurant restaurant = new Restaurant(13L, "Alitas BBQ", "carrera 13 #12-12",
                "456789",
                "data:image/jpeg;base64,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kfT4+ymkw7y8g4DzVxrI6yMu3d14yvyOo7QvGy/2LwPe3j47osvbKqCmjIjcHE52BuSeu2gWjwjETC+5za73h9R1rp9RFDUROlIxEEOI7cgV8bG58bg29r5XWBnx14358Vt9nIniZrgDulrrm2RGnzstjLT0k53w8NJ1sQD3grMjxCCINi6QHQA62tzIyChpNmiKYSvkGEEEEEZ967lqS9mFrc+OWij+0lundb7rb9tv2stWsjET/UfZ2/7R9rn/ADRWoYXPIa0XJ0CxqomSoeQNSdFciAbGBfgr1BSmV4YO88hxKvYtVBzgxn2bBut67anvWRVvbAwwMN3u+0eP9o/nzWqjYXENaLkmwCllG5ZuBm42xW9G+Gp7Vw04zjOnD8n2Wy2fouklBPuszPbwH85KaWWDhVCIYw3jq48ys9et2ZR9GhDT9RzPzsWZUS7x9xoMgqIhRaKgRERERERERERERVVFVERUKqiItPjmF9KN5vvjTrHIqIOaRkRYjUFdGWnxjBxL7bbB/k7qP7rB2rsrffyxfVxHP9q5TVODqu0Wkp6xkjRFPw9yTi3qPMfzrWJXUD4j7WbTo4aFWZonMcWvBBHArKosRdGN02ew6sdp3cl5/etk6lRkRlitn3OHHs4q+GFuceY5e3JYKotv6pDL9k/o3f236X6nf++5YlVh0sfvMNueo8QopKORoxAYhzbmPTTxXTJmnLQ8jksRFRVVVSoiIiKgXpZFPQyP9xjj12sPE5LN9Riiznkuf7ceZ7zw8lZipJHjFaw5nIfO5RPma3K9zyGawqOjfKd1gvzPAdpWbLUsgaY4DvPOTpeXU3+ePCxV4m5w6NgEcf3W8e08VhRsLiGtBJOgCmErIerBm4/2t6NHDv1XGAyZvyHL3P4VPmfmpXgGEdGOkePbOg+6P3TBcF6O0kli/gODf3K3gC3dlbKMZE031cBy7T2qnVVOLqN0QBVRF6FUVQohRERERERERERERERVVFVEREREVERERYldQMlFnjsI1HYVF6/ApI7lo328xr3j9lM0ss+r2bBU5uFjzGX/AFTRTvj005Lm5Cyqavlj9x7gORzHgVMqrDIpPfYL8xkfELT1OzI/5byOpwv5hYEmxquA4oTfuNj5K82sifk8eea15xje+0hif12sfHNU9dpzrT27Hle5dnpxoGu7HfvZY5wecf8AKPl+6gca9v1sJ72g+tl2NwdD6q965T8Ke/a8p/rAb9nBEzrtc/RWhhE/9p3l+6ux4BOdWhva4fS6+NNe7JjCO5gHrZDuBmXeqxqnE5ZMnPNuQyHksUKQ0+zH9yTuaPqf2W4pMKijza0X5nM/4U7NkVk5xSm3ebnyXJq4mZMHlkovQ4LLJnbcbzd9BxUnw3DI4R7IueLjqf2Wellv0mzIabMC7uZ+ZKjLUPk105IFVEWioERERFQohRERERERERERERERVVFVEREREVERERFVUREVVSyIiJZLIiIlksiIiWVVRERFVUVURERERERERUKIUREREREREREREREVVRVRERERFRERERERERERERERERERERERERVVFVEREREREREVCiFERERERERERERERFVERERERFRERERERERERERERERERERERERVRERERERERERUKIiIiIiIiIiIv//Z",
                user.getId(), "123456");


        Mockito.when(ownerHttpAdapter.getOwner((13L))).thenReturn(user);
        restaurantPersistencePort.saveRestaurant(restaurant);

        // Act
        restaurantUseCase.saveRestaurant(restaurant);

        // Assert
        Mockito.verify(restaurantPersistencePort, times(1)).saveRestaurant(restaurant);
    }

    @Test
    public void saveRestaurant_invalidRoleUser() {
        // Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setIdOwner(123L);// Id de usuario que no es propietario

        User user = new User();
        user.setIdRole(456L); // Valor no correspondiente a un rol

        Mockito.when(ownerHttpAdapter.getOwner(123L)).thenReturn(user);

        try {
            // Act
            restaurantUseCase.saveRestaurant(restaurant);
            fail("Expected UserNotRoleOwnerException to be thrown");
        } catch (UserNotBeAOwnerException ex) {
            // Assert
            // Valida que se haya lanzado la excepción correctamente
            Assertions.assertEquals("UserNotBeAOwnerException", ex.getClass().getSimpleName());
        }

        // Valida que no se haya llamado al método saveRestaurant
        Mockito.verify(restaurantPersistencePort, never()).saveRestaurant(any(Restaurant.class));
    }


}