package br.com.microservices.statefulauthapi.exception;

public record ExceptionDetails (int status, String message) {
}
