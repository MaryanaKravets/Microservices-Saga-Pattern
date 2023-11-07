package com.example.restoranservice.repository;

import com.example.restoranservice.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    List<Dish> findAllByActive(boolean isActive);

    List<Dish> findDishByName(String name);

    @Query(value = "SELECT * FROM Dishes d INNER JOIN Dishes_Products dp ON (d.id = dp.dish_id) LEFT JOIN Products p ON (p.id = dp.product_id) WHERE p.product_id IN :ingredients ", nativeQuery = true)
    List<Dish> findDishesByIngredientsIds(@Param("ingredients") List<String> ingredients);

    List<Dish> findDishByPriceBetween(float lowPrice, float upperPrice);

    List<Dish> findDishByRate(int rate);

    void deleteDishById(long id);
}
