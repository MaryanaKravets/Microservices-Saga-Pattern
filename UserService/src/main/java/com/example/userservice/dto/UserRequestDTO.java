package com.example.userservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequestDTO {
  private String firstName;
  private String lastName;
  private String email;
  private String password;
}
