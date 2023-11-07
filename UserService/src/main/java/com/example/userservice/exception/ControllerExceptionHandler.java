package com.example.userservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(value = NoSuchUserException.class)
  public ResponseEntity<ErrorResponse> handleNoSuchUserException(NoSuchUserException ex) {
    return ResponseEntity.badRequest().body(new ErrorResponse("user_empty", ex.getMessage()));
  }

  @ExceptionHandler(CannotSaveUserException.class)
  public ResponseEntity<ErrorResponse> handleCannotSaveUser(CannotSaveUserException ex) {
    return ResponseEntity.badRequest().body(new ErrorResponse("user_create_error" , "Cannot save user. Try to change some information"));
  }
}
