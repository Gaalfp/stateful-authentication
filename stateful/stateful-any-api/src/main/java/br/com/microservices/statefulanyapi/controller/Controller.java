package br.com.microservices.statefulanyapi.controller;

import br.com.microservices.statefulanyapi.dto.AnyResponse;
import br.com.microservices.statefulanyapi.service.AnyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class Controller {

    private final AnyService service;

    @GetMapping("/getData")
    public ResponseEntity<AnyResponse> getData(@RequestHeader String token) {
        return new ResponseEntity<>(service.getData(token), HttpStatus.OK);
    }

    @GetMapping("/sumnumbers")
    public ResponseEntity<AnyResponse> sumNumbers(@RequestHeader String accessToken, int a, int b) {
        return new ResponseEntity<>(service.sumNumbers(a, b, accessToken), HttpStatus.OK);
    }
}
