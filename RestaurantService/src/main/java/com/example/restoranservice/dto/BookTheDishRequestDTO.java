package com.example.restoranservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BookTheDishRequestDTO {

    private long orderId;
    private List<Long> dishId;
}
