package com.pragma.powerup.usermicroservice.domain.model;

import javax.management.relation.Role;

public class User {
    private Long id;
    private Restaurant person;
    private Role role;

    public User(Long id, Restaurant person, Role role) {
        this.id = id;
        this.person = person;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Restaurant getPerson() {
        return person;
    }

    public void setPerson(Restaurant person) {
        this.person = person;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
