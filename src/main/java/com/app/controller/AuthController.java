package com.app.controller;

import com.app.models.AuthRequest;
import com.app.models.AuthResponse;
import com.app.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static com.app.constants.ValidationConstants.REFRESH_TOKEN_NOT_BLANK_MESSAGE;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @PostMapping("/access")
    public AuthResponse getNewAccessToken(
            @RequestBody @NotBlank(message = REFRESH_TOKEN_NOT_BLANK_MESSAGE) String refreshToken) {
        return authService.getAccessToken(refreshToken);
    }

    @PostMapping("/refresh")
    public AuthResponse getNewRefreshToken(
            @RequestBody @NotBlank(message = REFRESH_TOKEN_NOT_BLANK_MESSAGE) String refreshToken) {
        return authService.refreshToken(refreshToken);
    }
}
