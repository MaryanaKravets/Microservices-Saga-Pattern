package com.example.restoranservice.controller;

import com.example.restoranservice.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RateController {

    private final RateService rateService;

    @PatchMapping("/rate/{dishId}")
    public ResponseEntity<Void> addRateToDish(@PathVariable(name = "dishId") long dishId,
                                              @RequestParam boolean isLiked) {

        rateService.updateDishRate(dishId, isLiked);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
