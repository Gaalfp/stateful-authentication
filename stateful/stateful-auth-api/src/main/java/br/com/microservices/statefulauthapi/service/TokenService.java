package br.com.microservices.statefulauthapi.service;

import br.com.microservices.statefulauthapi.dto.TokenData;
import br.com.microservices.statefulauthapi.exception.AuthenticationException;
import br.com.microservices.statefulauthapi.exception.ValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import static io.micrometer.common.util.StringUtils.isEmpty;

@Service
@AllArgsConstructor
public class TokenService {

    private static final Long SECONDS = 86400L;
    private static final String EMPTY_SPACE = " ";
    private static final Integer TOKEN_INDEX = 1;

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public String createToken(String username) {
        var accessToken = UUID.randomUUID().toString();
        var data = new TokenData(username);
        var jsonData = getJsonData(data);
        redisTemplate.opsForValue().set(accessToken, jsonData);
        redisTemplate.expireAt(accessToken, Instant.now().plusSeconds(SECONDS));
        return accessToken;
    }

    private String getJsonData(Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            return "";
        }
    }

    public TokenData getTokenData(String token) {
        var accessToken = extractToken(token);
        var jsonString = getRedisTokenValue(accessToken);
        try {
            return objectMapper.readValue(jsonString, TokenData.class);
        } catch (Exception e) {
            throw new AuthenticationException("Error Extracting Token");
        }
    }

    public boolean isTokenValid(String token) {
        var accessToken = extractToken(token);
        var data = getRedisTokenValue(accessToken);
        return !isEmpty(data);
    }

    public void deleteAccessToken(String token) {
        var accessToken = extractToken(token);
        redisTemplate.delete(accessToken);
    }

    private String getRedisTokenValue(String token) {
        return redisTemplate.opsForValue().get(token);
    }


    public String extractToken(String token) {
        if (token.isEmpty()) {
            throw new ValidationException("Token was not informed");
        }
        return token.contains(EMPTY_SPACE) ? token.split(EMPTY_SPACE)[TOKEN_INDEX] : token;
    }
}
