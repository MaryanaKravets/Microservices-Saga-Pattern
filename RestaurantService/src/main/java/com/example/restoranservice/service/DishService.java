package com.example.restoranservice.service;

import com.example.restoranservice.dto.BookTheDishRequestDTO;
import com.example.restoranservice.dto.DishResponseDTO;
import com.example.restoranservice.dto.SaveDishRequestDTO;
import com.example.restoranservice.dto.UpdateDishRequestDTO;
import com.example.restoranservice.exception.NoSuchDishException;
import com.example.restoranservice.exception.NoSuchProductException;
import com.example.restoranservice.model.Dish;

import java.util.List;

public interface DishService {

    DishResponseDTO getDishById(Long id) throws NoSuchDishException;

    List<DishResponseDTO> getDishByName(String name) throws NoSuchDishException;

    List<DishResponseDTO> getDishesByIngredients(List<Integer> ingredients);

    List<DishResponseDTO> getAllDishes();

    List<DishResponseDTO> getDishesByPrice(float lowPrice, float upperPrice);

    List<DishResponseDTO> getDishesByRate(int rate);

    void saveDish(SaveDishRequestDTO dto) throws NoSuchProductException;

    void deleteDishById(Long id);

    DishResponseDTO editDish(UpdateDishRequestDTO dto) throws NoSuchDishException, NoSuchProductException;

    void bookTheDish(BookTheDishRequestDTO bookTheDishRequestDTO);
}
