package com.pragma.powerup.squaremicroservice.domain.model;

public class Flan extends Dessert{

    private String complement;

    public Flan() {
        super();
    }


    public Flan(String typeDessert, String complement) {
        super(typeDessert);
        this.complement = complement;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }
}
