package br.com.microservices.statefulanyapi.dto;

public record AnyResponse(String status, int code, Object data) {
}
