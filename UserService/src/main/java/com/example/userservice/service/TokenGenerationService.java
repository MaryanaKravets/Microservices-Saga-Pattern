package com.example.userservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class TokenGenerationService {

  @Value("${token.expiration.time}")
  private String tokenExpiration;

  @Value("${inner.token.secret}")
  String tokenSecretKey;

  @Value("${token.secret}")
  String tokenSecret;

  public String generateToken(String email) {
    Map<String , Object> map = new HashMap<>();
    map.put("ROLE", "User");

    long exp = Long.parseLong(tokenExpiration);
    long l = System.currentTimeMillis() + exp;
    Date expiredDate = new Date(l);


    String token = Jwts.builder()
            .addClaims(map)
            .setSubject(email)
            .setExpiration(expiredDate)
            .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.decode(tokenSecret))
            .compact();

    return token;
  }

  public String parseToken(String jwtToken) {
    try{
      return Jwts.parser().setSigningKey(tokenSecretKey).parseClaimsJws(jwtToken).getBody().getSubject();
    } catch (Exception e) {
      log.info("exception " + e.getMessage());
      return StringUtils.EMPTY;
    }
  }


}
