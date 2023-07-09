package com.pragma.powerup.squaremicroservice.domain.model;

public class IceCream extends Dessert{
    private String flavor;

    public IceCream() {
        super();
    }

    public IceCream(String typeDessert, String flavor) {
        super(typeDessert);
        this.flavor = flavor;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }
}
