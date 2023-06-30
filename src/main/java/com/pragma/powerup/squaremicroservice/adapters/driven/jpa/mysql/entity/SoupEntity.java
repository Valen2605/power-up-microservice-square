package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "soup")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SoupEntity extends DishEntity {

    private String companion;
}
