package com.example.apigateway.secure.filter;

import com.example.apigateway.service.TokenManagementService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class CustomGlobalFilterConfiguration {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TokenManagementService tokenManagementService;

    @Bean
    public GlobalFilter globalFilter(){
        return ((exchange, chain) -> {
            logger.info("Pre filter ....");
            ServerHttpRequest request = exchange.getRequest();
            String authorizationHeader = StringUtils.EMPTY;
            if (request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            }
            String jwt = authorizationHeader.replace("Bearer", "");
            String userId = tokenManagementService.getSubject(jwt);
            if (Objects.nonNull(userId)){
                String token = tokenManagementService.generateInnerJWTToken(userId);
                logger.info("Inner token " + token);
                exchange.getRequest().mutate()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .build();
            }
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("Post filter ...");
                String authorizationHeader2 = StringUtils.EMPTY;
                if (request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    authorizationHeader2 = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                }
                String innerJwt = authorizationHeader2.replace("Bearer", "");
                String innerUserId = tokenManagementService.getSubject(innerJwt);
                if (Objects.nonNull(userId)){
                    String token = tokenManagementService.generateJWTToken(innerUserId);
                    exchange.getResponse().getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            }
        }));
    });
    }
}
