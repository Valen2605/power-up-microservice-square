package com.pragma.powerup.squaremicroservice.adapters.driving.http.adapter;

import com.pragma.powerup.squaremicroservice.configuration.security.Interceptor;
import com.pragma.powerup.squaremicroservice.domain.exceptions.UserNotFoundException;
import com.pragma.powerup.squaremicroservice.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class EmployeeHttpAdapter {

    private final RestTemplate restTemplate;

    @Value("${my.variables.url}")
    String url;

    @Autowired
    public EmployeeHttpAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CrossOrigin("*")
    @GetMapping("/{id}")
    public User getEmployee(@PathVariable Long id){

        String urlInfo = url + id;
        ResponseEntity<User> response = null;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",Interceptor.getToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);

        try{
            response = restTemplate.exchange(urlInfo, HttpMethod.GET, entity, User.class);
        }catch (HttpClientErrorException e){
            if(!e.getStatusCode().is2xxSuccessful()) throw new UserNotFoundException();

        }
        return response.getBody();
    }


}
