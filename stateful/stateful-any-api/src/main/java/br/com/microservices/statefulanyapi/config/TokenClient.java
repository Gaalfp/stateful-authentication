package br.com.microservices.statefulanyapi.config;

import br.com.microservices.statefulanyapi.dto.AuthUserResponse;
import br.com.microservices.statefulanyapi.dto.TokenDTO;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/auth")
public interface TokenClient {

    @PostExchange("/validateToken")
    TokenDTO validateToken(@RequestHeader String token);

    @GetExchange("/user")
    AuthUserResponse getAuthenticatedUser(@RequestHeader String token);
}
