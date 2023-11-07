package com.example.restoranservice.service.impl;

import com.example.restoranservice.dto.BookTheDishRequestDTO;
import com.example.restoranservice.dto.DishResponseDTO;
import com.example.restoranservice.dto.SaveDishRequestDTO;
import com.example.restoranservice.dto.UpdateDishRequestDTO;
import com.example.restoranservice.exception.NoSuchDishException;
import com.example.restoranservice.exception.NoSuchProductException;
import com.example.restoranservice.model.Dish;
import com.example.restoranservice.model.Product;
import com.example.restoranservice.model.ProductDTO;
import com.example.restoranservice.repository.DishRepository;
import com.example.restoranservice.service.DishService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final ModelMapper modelMapper;
    private final ProductServiceClientImpl productServiceClient;

    @Override
    public List<DishResponseDTO> getAllDishes() {
        List<Dish> dishes = dishRepository.findAllByActive(Boolean.TRUE);

        return dishes.stream()
                .map(this::convertDishToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DishResponseDTO getDishById(Long id) throws NoSuchDishException{
        Dish dish = dishRepository.findById(id).orElseThrow(NoSuchDishException::new);

        return convertDishToDto(dish);
    }

    @Override
    public List<DishResponseDTO> getDishByName(String name) throws NoSuchDishException {
        List<Dish> dishes = dishRepository.findDishByName(name);
        if (CollectionUtils.isEmpty(dishes)) {
            throw new NoSuchDishException();
        }
        return dishes.stream()
                .map(d -> convertDishToDto(d))
                .collect(Collectors.toList());
    }

    @Override
    public List<DishResponseDTO> getDishesByIngredients(List<Integer> ingredients) {
        List<Dish> dishes = dishRepository.findDishesByIngredientsIds(ingredients.stream().map(i -> String.valueOf(i)).collect(Collectors.toList()));

        return dishes.stream()
                .map(this::convertDishToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DishResponseDTO> getDishesByPrice(float lowPrice, float upperPrice) {
        List<Dish> dishes = dishRepository.findDishByPriceBetween(lowPrice, upperPrice);

        return dishes.stream()
                .map(this::convertDishToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DishResponseDTO> getDishesByRate(int rate) {
        List<Dish> dishes = dishRepository.findDishByRate(rate);

        return dishes.stream()
                .map(this::convertDishToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void saveDish(SaveDishRequestDTO dto) throws NoSuchProductException {
        Set<ProductDTO> products = dto.getIngredients().stream()
                .map(productServiceClient::getProductById)
                .filter(Objects::nonNull)
                .map(p -> new ProductDTO(p.getId()))
                .collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(products)) {
            dishRepository.save(convertToDish(dto, products));
        } else {
            throw new NoSuchProductException();
        }
    }
    @Override
    public void deleteDishById(Long id) {
        dishRepository.deleteDishById(id);
    }

    @Transactional
    @Override
    public DishResponseDTO editDish(UpdateDishRequestDTO dto) throws NoSuchDishException {
        Dish dish = dishRepository.findById(Long.parseLong(String.valueOf(dto.getId()))).orElseThrow(() -> new NoSuchDishException());
        Set<Product> products = dto.getIngredients().stream()
                .map(productServiceClient::getProductById)
                .collect(Collectors.toSet());
        updateDishData(dto, dish, products.stream().map(p -> new ProductDTO(p.getId())).collect(Collectors.toSet()));
        dishRepository.save(dish);
        return modelMapper.map(dish, DishResponseDTO.class);
    }

    @Override
    public void bookTheDish(BookTheDishRequestDTO bookTheDishRequestDTO) {

    }

    private Dish convertToDish(SaveDishRequestDTO dto, Set<ProductDTO> products){
        Dish dish = new Dish();
        dish.setName(dto.getName());
        dish.setDescription(dto.getDescription());
        dish.setImage(dto.getImage());
        dish.setPrice(dto.getPrice());
        dish.setStepsToPreparing(dto.getStepsToPreparing());
        dish.setTime(dto.getTime());
        dish.setCalories(dto.getCalories());
        dish.setDiscount(dto.getDiscount());
        dish.setActive(dto.isActive());
        dish.setIngredients(products);

        return dish;
    }

    private Dish updateDishData(UpdateDishRequestDTO dto, Dish dish, Set<ProductDTO> products){
        dish.getIngredients().clear();
        dish.setName(dto.getName());
        dish.setDescription(dto.getDescription());
        dish.setImage(dto.getImage());
        dish.setPrice(dto.getPrice());
        dish.setStepsToPreparing(dto.getStepsToPreparing());
        dish.setTime(dto.getTime());
        dish.setCalories(dto.getCalories());
        dish.setDiscount(dto.getDiscount());
        dish.setActive(dto.isActive());
        dish.setIngredients(products);

        return dish;
    }

    public DishResponseDTO convertDishToDto(Dish dish) {
        DishResponseDTO dto = modelMapper.map(dish, DishResponseDTO.class);
        dto.setProducts(dish.getIngredients().stream()
                .map(i -> productServiceClient.getProductById(i.getProductId()))
                .collect(Collectors.toSet()));

        return dto;
    }
}
