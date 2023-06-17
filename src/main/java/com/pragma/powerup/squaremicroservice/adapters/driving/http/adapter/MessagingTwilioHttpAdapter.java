package com.pragma.powerup.squaremicroservice.adapters.driving.http.adapter;

import com.pragma.powerup.squaremicroservice.configuration.security.Interceptor;
import com.pragma.powerup.squaremicroservice.domain.exceptions.UserNotFoundException;
import com.pragma.powerup.squaremicroservice.domain.model.User;
import com.pragma.powerup.squaremicroservice.domain.spi.IClientHttpAdapterPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.spi.IMessagingTwilioHttpAdapterPersistencePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class MessagingTwilioHttpAdapter implements IMessagingTwilioHttpAdapterPersistencePort {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${my.variables.uri}")
    String uri;

    @Override
    public void getMessaging(String message, String number) {
        String urlMessage = uri + number;
        String token = Interceptor.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(message,headers);

        try {
            restTemplate.exchange(urlMessage, HttpMethod.POST, entity, String.class);
        } catch (HttpClientErrorException error) {
            throw new UserNotFoundException();
        }
    }


}

