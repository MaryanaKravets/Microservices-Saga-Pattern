package com.example.userservice.service;

import com.example.userservice.dto.LoginRequestDTO;
import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

  void saveUser(UserRequestDTO userRequestDTO);// return location
  User login(LoginRequestDTO loginRequestDTO);
  void deleteUser(Long id);

  List<User> getAllUsers();

  String getUserInfo(String jwtTokenCookie);
}
