package com.pragma.powerup.squaremicroservice.domain.model;

public class Dish {

    private Long id;
    private String name;
    private Long idCategory;
    private String description;
    private Integer price;
    private Long idRestaurant;
    private String urlImage;
    private Boolean active;

    public Dish() {
    }

    public Dish(Long id, String name, Long idCategory, String description, Integer price, Long idRestaurant, String urlImage, Boolean active) {
        this.id = id;
        this.name = name;
        this.idCategory = idCategory;
        this.description = description;
        this.price = price;
        this.idRestaurant = idRestaurant;
        this.urlImage = urlImage;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
