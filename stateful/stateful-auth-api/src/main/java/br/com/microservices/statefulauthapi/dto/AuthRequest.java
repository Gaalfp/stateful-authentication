package br.com.microservices.statefulauthapi.dto;

public record AuthRequest(String username, String password) {
}
