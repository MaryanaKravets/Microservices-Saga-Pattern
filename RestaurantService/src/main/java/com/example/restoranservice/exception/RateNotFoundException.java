package com.example.restoranservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such rate")
public class RateNotFoundException extends RuntimeException{
}
