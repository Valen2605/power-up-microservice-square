package com.pragma.powerup.squaremicroservice.domain.model;

public class Meat extends DishOrder{
    private Integer grams;

    public Meat() {
        super();
    }

    public Meat(Integer grams) {
        this.grams = grams;
    }

    public Integer getGrams() {
        return grams;
    }

    public void setGrams(Integer grams) {
        this.grams = grams;
    }
}
