package com.example.authservice.service;

import com.example.authservice.client.UserClient;
import com.example.authservice.dto.request.AuthRequest;
import com.example.authservice.dto.response.AuthResponse;
import com.example.authservice.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserClient userClient;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse authenticate(AuthRequest request) {
        // 1. Запрашиваем пользователя из user-service
        UserResponse userResponse = userClient.getUserByUsername(request.getUsername());

        // 2. Проверяем пароль
        if (!passwordEncoder.matches(request.getPassword(), userResponse.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // 3. Генерируем токен
        String jwtToken = jwtService.generateToken(
                User.builder()
                        .username(userResponse.getUsername())
                        .password(userResponse.getPassword())
                        .roles("USER")  // Можно добавить роли из userResponse
                        .build()
        );

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}