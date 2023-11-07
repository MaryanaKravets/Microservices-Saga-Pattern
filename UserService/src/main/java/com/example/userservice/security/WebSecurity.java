package com.example.userservice.security;

import com.example.userservice.security.filter.AuthenticationFilter;
import com.example.userservice.security.filter.ValidateTokenFilter;
import com.example.userservice.service.TokenGenerationService;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.Filter;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter{

  private final Environment environment;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserService userService;
  private final TokenGenerationService tokenGenerationService;


  //@Bean SecurityFilterChain
  @Override
  protected void configure(HttpSecurity http) throws Exception {

//    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//    AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

    http
            .cors().and()
            .csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/**").permitAll();
//                    .and()
//                            .addFilter(new AuthenticationFilter(authenticationManager, tokenGenerationService))
//            .addFilter(new ValidateTokenFilter(environment))
//                                    .authenticationManager(authenticationManager)
//                                            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    return http.build();
                   // .requestMatchers(HttpMethod.POST,)
    //http.addFilterBefore(new ValidateTokenFilter(environment), UsernamePasswordAuthenticationFilter.class);
    http.addFilterBefore(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    http.addFilterAfter(new ValidateTokenFilter(environment), UsernamePasswordAuthenticationFilter.class);
  }

  private Filter getAuthenticationFilter() throws Exception {

    // set authenticationManager() because in filter getAuthenticationManager()
    AuthenticationFilter authenticationFilter = new AuthenticationFilter(tokenGenerationService);
    authenticationFilter.setAuthenticationManager(authenticationManager());
    return authenticationFilter;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource()
  {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:4200/"));
    configuration.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept","Authorization"));
    configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
