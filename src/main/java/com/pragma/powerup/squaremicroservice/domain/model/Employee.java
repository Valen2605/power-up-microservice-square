package com.pragma.powerup.squaremicroservice.domain.model;


public class Employee {

    private Long id;

    private Long idEmployee;
    private String dniNumber;

    private Long idRestaurant;

    public Employee(Long id, Long idEmployee, String dniNumber, Long idRestaurant) {
        this.id = id;
        this.idEmployee = idEmployee;
        this.dniNumber = dniNumber;
        this.idRestaurant = idRestaurant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getDniNumber() {
        return dniNumber;
    }

    public void setDniNumber(String dniNumber) {
        this.dniNumber = dniNumber;
    }

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }
}