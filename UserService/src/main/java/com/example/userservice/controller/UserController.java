package com.example.userservice.controller;

import com.example.userservice.dto.UserInfoResponseDTO;
import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.dto.UserResponseDTO;
import com.example.userservice.exception.NoSuchUserException;
import com.example.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService userService;
  private final ObjectMapper objectMapper;

  @Timed("getAllUsers")
  @GetMapping("/all")
  public ResponseEntity<List<UserResponseDTO>> getAllUsers() {

    return ResponseEntity.ok().body(userService.getAllUsers().stream()
            .map(o -> objectMapper.convertValue(o, UserResponseDTO.class))
            .collect(Collectors.toList()));
  }

  @Timed("saveUser")
  @PostMapping
  public ResponseEntity<Void> saveUser(@RequestBody UserRequestDTO dto) throws NoSuchUserException {
    userService.saveUser(dto);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUserById(@PathVariable(name = "id") long id) {
    userService.deleteUser(id);

    return ResponseEntity.noContent().build();
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Set-Cookie","AUTHORIZATION=; Max-Age=0; Path=/;");
    return ResponseEntity.ok().headers(headers).build();
  }

  @GetMapping("/user-info")
  public ResponseEntity<UserInfoResponseDTO> getUserInfo(@CookieValue(value = "AUTHORIZATION") String jwtTokenCookie){
    String firstLastUserName = userService.getUserInfo(jwtTokenCookie);
    return ResponseEntity.ok(new UserInfoResponseDTO(firstLastUserName));
  }
}
