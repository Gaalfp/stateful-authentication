package br.com.microservices.statefulanyapi.service;

import br.com.microservices.statefulanyapi.dto.AnyResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class AnyService {

    public final TokenService tokenService;

    public AnyResponse getData(String token) {
        tokenService.validateToken(token);
        var authUser = tokenService.getAuthenticatedUser(token);
        var ok = HttpStatus.OK;
        return new AnyResponse(ok.name(), ok.value(), authUser);
    }


    public AnyResponse sumNumbers(int a, int b, String accessToken) {
        tokenService.validateToken(accessToken);
        var result = a + b;
        return new AnyResponse(HttpStatus.OK.name(), HttpStatus.OK.value(), result);
    }
}
