package com.pragma.powerup.squaremicroservice.domain.model;

public class DishOrder {

    private Long id;
    private String typeDish;
    private Integer priority;

    private String companion;
    private Integer grams;
    private String typeDessert;
    private String complement;
    private String flavor;

    public DishOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeDish() {
        return typeDish;
    }

    public void setTypeDish(String typeDish) {
        this.typeDish = typeDish;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getCompanion() {
        return companion;
    }

    public void setCompanion(String companion) {
        this.companion = companion;
    }

    public Integer getGrams() {
        return grams;
    }

    public void setGrams(Integer grams) {
        this.grams = grams;
    }

    public String getTypeDessert() {
        return typeDessert;
    }

    public void setTypeDessert(String typeDessert) {
        this.typeDessert = typeDessert;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }
}
