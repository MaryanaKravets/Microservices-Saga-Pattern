package com.example.restoranservice.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private String id;
    private String name;
    private float weight;
    private String description;
}
