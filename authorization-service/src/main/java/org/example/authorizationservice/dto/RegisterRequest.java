package org.example.authorizationservice.dto;

public record RegisterRequest(String username, String password, String email, String firstName, String lastName) {
}
