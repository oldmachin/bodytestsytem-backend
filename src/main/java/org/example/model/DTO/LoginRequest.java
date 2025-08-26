package org.example.model.DTO;

public record LoginRequest(
        String username,
        String password
) {
}
