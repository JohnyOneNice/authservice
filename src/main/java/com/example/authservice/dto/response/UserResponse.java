package com.example.authservice.dto.response;

import lombok.Data;
import java.util.UUID;

@Data
public class UserResponse {
    private UUID id;
    private String username;
    private String password; // Нужен для проверки в AuthService
}