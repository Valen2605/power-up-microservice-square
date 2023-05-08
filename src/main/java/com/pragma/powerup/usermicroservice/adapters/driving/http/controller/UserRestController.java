package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.pragma.powerup.usermicroservice.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/getOwner")
public class UserRestController {

    private final RestTemplate restTemplate;

    @Autowired
    public UserRestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CrossOrigin("*")
    @GetMapping("/{id}")
    public User getOwner(@PathVariable Long id){
        String url = "http://localhost:8090/user/" + id;
        User user = restTemplate.getForObject(url, User.class);
        return user;

    }
}