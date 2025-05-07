package com.example.authservice.client;

import com.example.authservice.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${user-service.url}") // URL из application.properties
public interface UserClient {

    @GetMapping("/api/user/internal/username/{username}")
    UserResponse getUserByUsername(@PathVariable String username);
}