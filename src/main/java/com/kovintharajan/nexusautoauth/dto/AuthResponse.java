package com.kovintharajan.nexusautoauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;

    private UserResponse user;
}
