package br.com.microservices.statefulauthapi.service;

import br.com.microservices.statefulauthapi.dto.AuthRequest;
import br.com.microservices.statefulauthapi.dto.AuthUserResponse;
import br.com.microservices.statefulauthapi.dto.TokenDTO;

import br.com.microservices.statefulauthapi.exception.AuthenticationException;
import br.com.microservices.statefulauthapi.exception.ValidationException;
import br.com.microservices.statefulauthapi.model.User;
import br.com.microservices.statefulauthapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;



    public TokenDTO login(AuthRequest authRequest) {
        var user = findByUsername(authRequest.username());
        var accessToken = tokenService.createToken(user.getUsername());
        validatePassword(authRequest.password(), user.getPassword());
        return new TokenDTO(accessToken);
    }

    public TokenDTO validateToken(String accessToken) {
        validateExistingToken(accessToken);
        var validated = tokenService.isTokenValid(accessToken);
        if(validated){
            return new TokenDTO(accessToken);
        }
        throw new AuthenticationException("Token is invalid");
    }

    public AuthUserResponse getAuthenticatedUser(String accessToken) {
        var tokenData = tokenService.getTokenData(accessToken);
        var user = findByUsername(tokenData.username());
        return new AuthUserResponse(user.getId(), user.getUsername());
    }

    public void logout(String accessToken) {
        tokenService.deleteAccessToken(accessToken);
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ValidationException("User not found"));
    }


    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new ValidationException("The password is invalid");
        }
    }
    private void validateExistingToken(String accessToken) {
        if(accessToken.isEmpty()) {
            throw new ValidationException("Token was not informed");
        }

    }
}
