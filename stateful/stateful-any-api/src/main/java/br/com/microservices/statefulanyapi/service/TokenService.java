package br.com.microservices.statefulanyapi.service;

import br.com.microservices.statefulanyapi.config.TokenClient;
import br.com.microservices.statefulanyapi.dto.AuthUserResponse;
import br.com.microservices.statefulanyapi.exception.AuthenticationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class TokenService {

    private final TokenClient tokenClient;

    public void validateToken(String token) {
         try {
             log.info("Validating token: {}", token);
             var response = tokenClient.validateToken(token);
             log.info("Token validated: {}", response);
         }catch (Exception e) {
             log.error("Error validating token: {}", e.getMessage());
             throw new AuthenticationException("Error validating token: " + e.getMessage());
         }
    }

    public AuthUserResponse getAuthenticatedUser(String token) {
        try {
            log.info("Validating user: {}", token);
            var response = tokenClient.getAuthenticatedUser(token);
            log.info("User validated: {}", response);
            return response;
        }catch (Exception e) {
            log.error("Error validating user: {}", e.getMessage());
            throw new AuthenticationException("Error validating user: " + e.getMessage());
        }
    }

}
