package com.pragma.powerup.squaremicroservice.domain.model;

public class Soup extends DishOrder{

    private String companion;

    public Soup() {
        super();
    }

    public Soup(String companion) {
        this.companion = companion;
    }

    public String getCompanion() {
        return companion;
    }

    public void setCompanion(String companion) {
        this.companion = companion;
    }
}
