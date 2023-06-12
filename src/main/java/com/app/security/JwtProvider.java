package com.app.security;

import com.app.entity.Profile;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static com.app.constants.SecurityConstants.VEHICLE_LIST_ROLES;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;

@Component
public class JwtProvider {
    private static final String USER = "ROLE_USER";
    private static final long ACCESS_TOKEN_EXPIRATION_MINUTES = 5;
    private static final long REFRESH_TOKEN_EXPIRATION_DAYS = 30;
    private static final String VEHICLE_LIST_SECRET_KEY_FOR_ACCESS_TOKEN = "vehicleListSecretKeyAccess";
    private static final String VEHICLE_LIST_SECRET_KEY_FOR_REFRESH_TOKEN = "vehicleListSecretKeyRefresh";
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    public String generateAccessToken(Profile profile) {
        Instant accessExpirationInstant = Instant.now().plus(ACCESS_TOKEN_EXPIRATION_MINUTES, MINUTES);
        Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setIssuer(profile.getLogin())
                .addClaims(Map.of(VEHICLE_LIST_ROLES, USER))
                .setExpiration(accessExpiration)
                .signWith(SignatureAlgorithm.HS256, VEHICLE_LIST_SECRET_KEY_FOR_ACCESS_TOKEN)
                .compact();
    }

    public String generateRefreshToken(Profile profile) {
        Instant refreshExpirationInstant = Instant.now().plus(REFRESH_TOKEN_EXPIRATION_DAYS, DAYS);
        Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setIssuer(profile.getLogin())
                .setExpiration(refreshExpiration)
                .signWith(SignatureAlgorithm.HS256, VEHICLE_LIST_SECRET_KEY_FOR_REFRESH_TOKEN)
                .compact();
    }

    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, VEHICLE_LIST_SECRET_KEY_FOR_ACCESS_TOKEN);
    }

    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, VEHICLE_LIST_SECRET_KEY_FOR_REFRESH_TOKEN);
    }

    public Claims getAccessClaims(String token) {
        return getClaims(token, VEHICLE_LIST_SECRET_KEY_FOR_ACCESS_TOKEN);
    }

    public Claims getRefreshClaims(String token) {
        return getClaims(token, VEHICLE_LIST_SECRET_KEY_FOR_REFRESH_TOKEN);
    }


    private boolean validateToken(String token, String secret) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature", e);
        } catch (MalformedJwtException e) {
            logger.error("Malformed JWT", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty", e);
        } catch (Exception e) {
            logger.error("Unexpected error while validating JWT", e);
        }
        return false;
    }

    private Claims getClaims(String token, String secret) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
