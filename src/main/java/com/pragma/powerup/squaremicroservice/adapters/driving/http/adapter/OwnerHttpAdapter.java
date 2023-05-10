package com.pragma.powerup.squaremicroservice.adapters.driving.http.adapter;

import com.pragma.powerup.squaremicroservice.domain.model.User;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Component
public class OwnerHttpAdapter {

    private final RestTemplate restTemplate;

    private String message;

    @Value("${my.variables.url}")
    String url;

    @Autowired
    public OwnerHttpAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CrossOrigin("*")
    @GetMapping("/{id}")
    public User getOwner(@PathVariable Long id){
        String urlInfo = url + id;
        User user = restTemplate.getForObject(urlInfo, User.class);
        return user;
    }

    // Aquí Validación
}
