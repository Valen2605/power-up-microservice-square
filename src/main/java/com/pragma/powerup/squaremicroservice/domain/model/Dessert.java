package com.pragma.powerup.squaremicroservice.domain.model;

public class Dessert extends DishOrder{


    private String typeDessert;

    public Dessert() {
        super();
    }

    public Dessert(String typeDessert) {
        this.typeDessert = typeDessert;
    }

    public String getTypeDessert() {
        return typeDessert;
    }

    public void setTypeDessert(String typeDessert) {
        this.typeDessert = typeDessert;
    }
}
