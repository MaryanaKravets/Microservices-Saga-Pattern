package com.example.userservice.exception;

public class NoSuchUserException extends RuntimeException{

  public NoSuchUserException(String cause){
    super(cause);
  }
}
