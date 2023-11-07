package com.example.restoranservice.security.filter;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ValidateTokenFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Environment environment;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isNotEmpty(bearerToken)) {
            String jwt = bearerToken.replace("Bearer", "");
            logger.info("Token {}", jwt);
            if (isJwtValid(jwt)) {
                filterChain.doFilter(request, response);
            } else {
                logger.info("Jwt token is invalid {}", jwt);
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                return;
            }
        } else {
            logger.info("Empty or absent jwt token ");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        }
    }

    private boolean isJwtValid(String jwt) {
        boolean returnValue = true;
        String subject = StringUtils.EMPTY;
        logger.info("token secret {}", environment.getProperty("inner.token.secret"));
        logger.info("token expiration time {}", environment.getProperty("token.expiration.time"));

        try {
            logger.info("token secret {}", environment.getProperty("inner.token.secret"));
            subject = Jwts.parser().setSigningKey(environment.getProperty("inner.token.secret"))
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            logger.error(e.getMessage());
            returnValue = false;
        }
        if (Objects.isNull(subject) || StringUtils.isEmpty(subject)) {
            returnValue = false;
        }
        return returnValue;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        List<String> excludeUrlPatterns = new ArrayList<>(Arrays.asList("/actuator/*", "/actuator"));
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return excludeUrlPatterns.stream()
                .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
    }
}

