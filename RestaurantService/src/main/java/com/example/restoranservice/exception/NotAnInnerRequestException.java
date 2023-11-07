package com.example.restoranservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Request from external address")
public class NotAnInnerRequestException extends RuntimeException {

    private String msg;

    public NotAnInnerRequestException(String msg) {
        super(msg);
    }
}

