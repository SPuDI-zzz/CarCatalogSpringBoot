package com.app.service;

import com.app.entity.Profile;
import com.app.exception.AuthException;
import com.app.models.AuthRequest;
import com.app.models.AuthResponse;
import com.app.repository.ProfileRepository;
import com.app.security.JwtProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private static final String INCORRECT_LOGIN_OR_PASSWORD = "Incorrect login or password";
    private static final String REFRESH_TOKEN_IS_INVALID = "Refresh token is invalid";
    private static final String PROFILE_WITH_LOGIN_NOT_FOUND = "Profile with login '%s' not found";
    private final ProfileRepository profileRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(AuthRequest authRequest) {
        Profile profile = profileRepository.findProfileByLogin(authRequest.getLogin())
                .orElseThrow(() -> new AuthException(INCORRECT_LOGIN_OR_PASSWORD));

        if (!passwordEncoder.matches(authRequest.getPassword(), profile.getPassword())) {
            throw new AuthException(INCORRECT_LOGIN_OR_PASSWORD);
        }

        String accessToken = jwtProvider.generateAccessToken(profile);
        String refreshToken = jwtProvider.generateRefreshToken(profile);
        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse getAccessToken(String refreshToken) {
        Profile profile = getProfileByRefreshToken(refreshToken);
        String accessToken = jwtProvider.generateAccessToken(profile);
        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse refreshToken(String refreshToken) {
        Profile profile = getProfileByRefreshToken(refreshToken);
        String accessToken = jwtProvider.generateAccessToken(profile);
        String newRefreshToken = jwtProvider.generateRefreshToken(profile);
        return new AuthResponse(accessToken, newRefreshToken);
    }

    private Profile getProfileByRefreshToken(String refreshToken) {
        if (!jwtProvider.validateRefreshToken(refreshToken)) {
            throw new AuthException(REFRESH_TOKEN_IS_INVALID);
        }

        Claims claims = jwtProvider.getRefreshClaims(refreshToken);
        String login = claims.getIssuer();
        return profileRepository.findProfileByLogin(login)
                .orElseThrow(() -> new AuthException(PROFILE_WITH_LOGIN_NOT_FOUND.formatted(login)));
    }
}
