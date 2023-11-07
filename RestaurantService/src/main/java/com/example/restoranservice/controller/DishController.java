package com.example.restoranservice.controller;

import com.example.restoranservice.dto.DishResponseDTO;
import com.example.restoranservice.dto.SaveDishRequestDTO;
import com.example.restoranservice.dto.UpdateDishRequestDTO;
import com.example.restoranservice.exception.NoSuchDishException;
import com.example.restoranservice.exception.NoSuchProductException;
import com.example.restoranservice.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/dish")
public class DishController {

    private final DishService dishService;
    private final Environment env;

    @GetMapping("/port")
    public ResponseEntity<String> getPort() {

        return ResponseEntity.ok().body("Current port " + env.getProperty("local.server.port") + "secret " + env.getProperty("token.secret"));
    }


    @GetMapping
    public ResponseEntity<List<DishResponseDTO>> getAllDishes() {

        return ResponseEntity.ok().body(dishService.getAllDishes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishResponseDTO> getDishById(@PathVariable(name = "id") long id) throws NoSuchDishException{

        return ResponseEntity.ok().body(dishService.getDishById(id));
    }

    @GetMapping("/name")
    public ResponseEntity<List<DishResponseDTO>> getDishByName(@RequestParam(name = "name") String name) throws NoSuchDishException {

        return ResponseEntity.ok().body(dishService.getDishByName(name));
    }

    @PostMapping("/filter/ingredients")
    public ResponseEntity<List<DishResponseDTO>> getDishesByIngredients(@RequestBody List<Integer> ingredients) {

        return ResponseEntity.ok().body(dishService.getDishesByIngredients(ingredients));
    }

    @GetMapping("/price")
    public ResponseEntity<List<DishResponseDTO>> getDishesByPrice(@RequestParam(name = "lowPrice") float lowPrice,
                                                                  @RequestParam(name = "upperPrice") float upperPrice) {

        return ResponseEntity.ok().body(dishService.getDishesByPrice(lowPrice, upperPrice));
    }

    @GetMapping("/rate")
    public ResponseEntity<List<DishResponseDTO>> getDishesByRate(@RequestParam(name = "rate") int rate) {

        return ResponseEntity.ok().body(dishService.getDishesByRate(rate));
    }

    @PostMapping
    public ResponseEntity<Void> saveDish(@RequestBody SaveDishRequestDTO dto) throws NoSuchProductException {
        dishService.saveDish(dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDishById(@PathVariable(name = "id") long id) {
        dishService.deleteDishById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<DishResponseDTO> editDish(@RequestBody UpdateDishRequestDTO dto) throws NoSuchDishException, NoSuchProductException {
        DishResponseDTO responseDTO = dishService.editDish(dto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
