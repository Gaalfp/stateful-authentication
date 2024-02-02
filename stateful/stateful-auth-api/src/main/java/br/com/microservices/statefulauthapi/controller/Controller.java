package br.com.microservices.statefulauthapi.controller;

import br.com.microservices.statefulauthapi.dto.AuthRequest;
import br.com.microservices.statefulauthapi.dto.AuthUserResponse;
import br.com.microservices.statefulauthapi.dto.TokenDTO;
import br.com.microservices.statefulauthapi.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("api/auth")
public class Controller {

    private final AuthService service;

    @PostMapping("/login")
    public TokenDTO login(@RequestBody AuthRequest authRequest) {
        return service.login(authRequest);
    }

    @PostMapping("/validateToken")
    public TokenDTO validateToken(@RequestHeader String token) {
        return service.validateToken(token);
    }

    @PostMapping("/logout")
    public Map<Object, Object> logout(@RequestHeader String token) {
        service.logout(token);
        var response = new HashMap<>();
        response.put("status", HttpStatus.OK.name());
        response.put("code", HttpStatus.OK.value());
        return response;
    }

    @GetMapping("/user")
    public AuthUserResponse getAuthenticatedUser(@RequestHeader String token) {
        return service.getAuthenticatedUser(token);
    }
}
