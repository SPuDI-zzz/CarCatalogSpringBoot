package com.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthResponse {
    private final String type = "Bearer";

    private final String accessToken;

    private final String refreshToken;
}
