package com.pragma.powerup.squaremicroservice.adapters.driving.http.adapter;


import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.TraceabilityRequestDto;
import com.pragma.powerup.squaremicroservice.configuration.security.Interceptor;
import com.pragma.powerup.squaremicroservice.domain.exceptions.UserNotFoundException;
import com.pragma.powerup.squaremicroservice.domain.spi.IMessagingTwilioHttpAdapterPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.spi.ITraceabilityHttpAdapterPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.utility.StatusEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

public class TraceabilityHttpAdapter implements ITraceabilityHttpAdapterPersistencePort {
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${my.variables.urltrace}")
    String url;


    @Override
    public void getTraceability(TraceabilityRequestDto traceabilityRequestDto) {
        String urlMessage = url + traceabilityRequestDto;
        String token = Interceptor.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TraceabilityRequestDto> entity = new HttpEntity<>(traceabilityRequestDto,headers);

        try {
            restTemplate.exchange(urlMessage, HttpMethod.POST, entity, String.class);
        } catch (HttpClientErrorException error) {
            throw new UserNotFoundException();
        }
    }
}
