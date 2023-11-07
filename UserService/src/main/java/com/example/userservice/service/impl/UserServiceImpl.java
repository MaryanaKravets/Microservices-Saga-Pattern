package com.example.userservice.service.impl;

import com.example.userservice.dto.LoginRequestDTO;
import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.exception.CannotSaveUserException;
import com.example.userservice.exception.ExceptionMessages;
import com.example.userservice.exception.NoSuchUserException;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.TokenGenerationService;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, ExceptionMessages {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final TokenGenerationService tokenGenerationService;

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public void saveUser(UserRequestDTO userRequestDTO) {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    User user = modelMapper.map(userRequestDTO, User.class);
    user.setPublicUserId(UUID.randomUUID().toString());
    user.setEncryptedPassword(bCryptPasswordEncoder.encode(userRequestDTO.getPassword()));
    try {
      userRepository.save(user);
    } catch (Exception e) {
      throw new CannotSaveUserException();
    }
  }

  @Override
  public User login(LoginRequestDTO loginRequestDTO) {
    return userRepository.findByEmailAndEncryptedPassword(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
            .orElseThrow(() -> new NoSuchUserException(NO_SUCH_USER));

  }

  @Override
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username).orElseThrow(() -> new NoSuchUserException(NO_SUCH_USER));
    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
  }

  public String getUserInfo(String jwtTokenCookie) {
    String email = tokenGenerationService.parseToken(jwtTokenCookie);
   User user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchUserException("No such user"));
   return user.getFirstName() + "_" + user.getLastName();
  }
}
