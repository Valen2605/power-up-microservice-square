package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "dishOrder")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DishOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeDish;
    private String companion;
    private Integer grams;
    private Integer priority;
    private String typeDessert;
    private String complement;
    private String flavor;
}
