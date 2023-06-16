package com.pragma.powerup.squaremicroservice.domain.model;

import java.time.LocalDate;

public class Order {

    private Long id;
    private Long idClient;
    private LocalDate dateOrder;
    private String status;
    private Long idChef;
    private Restaurant restaurant;
    private String codeOrder;

    public Order() {
    }

    public Order(Long id, Long idClient, LocalDate dateOrder, String status, Long idChef, Restaurant restaurant, String codeOrder) {
        this.id = id;
        this.idClient = idClient;
        this.dateOrder = dateOrder;
        this.status = status;
        this.idChef = idChef;
        this.restaurant = restaurant;
        this.codeOrder = codeOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public LocalDate getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(LocalDate dateOrder) {
        this.dateOrder = dateOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getIdChef() {
        return idChef;
    }

    public void setIdChef(Long idChef) {
        this.idChef = idChef;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getCodeOrder() {
        return codeOrder;
    }

    public void setCodeOrder(String codeOrder) {
        this.codeOrder = codeOrder;
    }
}
