package com.example.restoranservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NoSuchDishException.class)
    @ResponseBody
    public ResponseEntity<Object> handleNoSuchDishException(NoSuchDishException ex) {
        return new ResponseEntity<Object>(ExceptionMessages.NO_SUCH_DISH, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchProductException.class)
    @ResponseBody
    public ResponseEntity<Object> handleNoSuchProductException(NoSuchProductException ex) {
        return new ResponseEntity<Object>(ExceptionMessages.NO_SUCH_PRODUCT, HttpStatus.BAD_REQUEST);
    }
}
