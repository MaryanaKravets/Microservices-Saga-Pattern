package com.example.restoranservice.dto;

import com.example.restoranservice.model.Product;
import lombok.Data;

import java.util.Set;

@Data
public class DishResponseDTO {

    private String name;
    private String description;
    private String image;
    private float price;
    private Set<Product> products;
}
