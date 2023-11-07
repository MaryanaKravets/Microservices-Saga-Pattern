package com.example.restoranservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "dishes")
public class Dish implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String image;
    private float price;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Dishes_Products", joinColumns = {@JoinColumn(name = "dish_id")}, inverseJoinColumns = {@JoinColumn(name = "product_id")})
    private Set<ProductDTO> ingredients = new HashSet<>();
    @Column(name = "steps_to_preparing")
    private String stepsToPreparing;
    private String time;
    private int calories;
    private int discount;
    private int rate;
    @Column(name = "active")
    private boolean active;
}
