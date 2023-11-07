package com.example.restoranservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class UpdateDishRequestDTO {

    private long id;
    private String name;
    private String description;
    private String image;
    private float price;
    private Set<String> ingredients;
    private String stepsToPreparing;
    private String time;
    private int calories;
    private int discount;
    private boolean active;
}
