package com.example.userservice.security.filter;

import com.example.userservice.dto.LoginRequestDTO;
import com.example.userservice.service.TokenGenerationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final TokenGenerationService tokenGenerationService;

  public AuthenticationFilter(AuthenticationManager authenticationManager, TokenGenerationService tokenGenerationService) {
    super(authenticationManager);
    this.tokenGenerationService = tokenGenerationService;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    System.out.println("attempt authent");
    try {
      LoginRequestDTO loginRequestDTO = new ObjectMapper()
              .readValue(request.getInputStream(), LoginRequestDTO.class);

      System.out.println("try to return");
      return getAuthenticationManager().authenticate(
              new UsernamePasswordAuthenticationToken(
                      loginRequestDTO.getEmail(),
                      loginRequestDTO.getPassword(),
                      new ArrayList<>()
              )
      );
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

    String username = ((User)authResult.getPrincipal()).getUsername();

    String token = tokenGenerationService.generateToken(username);
    Cookie cookie = new Cookie("AUTHORIZATION",  token);
    cookie.setPath("/");
    cookie.setMaxAge(600000);
    response.addCookie(cookie);
  }
}
