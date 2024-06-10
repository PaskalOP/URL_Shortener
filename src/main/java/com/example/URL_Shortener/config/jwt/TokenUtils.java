package com.example.URL_Shortener.config.jwt;

import com.example.URL_Shortener.config.Config;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * Утилітарний клас для роботи з JWT токенами.
 * Включає методи для створення, парсингу та валідації токенів.
 */
@Slf4j
@Component
public class TokenUtils {

    @Autowired
    private Config config;

    /**
     * Метод для генерації JWT токена.
     *
     * @param authentication Authentication - об'єкт аутентифікації користувача
     * @return String - згенерований JWT токен
     */
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setId(userPrincipal.getId().toString())
                .setSubject(userPrincipal.getUsername())
                .claim("Authorities", userPrincipal.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + config.getJwtExpiration()))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Метод для отримання ключа шифрування з секрету.
     *
     * @return SecretKey - ключ шифрування
     */
    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(config.getJwtSecret()));
    }

    /**
     * Метод для отримання ID користувача з JWT токена.
     *
     * @param token String - JWT токен
     * @return UUID - ID користувача
     */
    public UUID getUserIdFromJwtToken(String token) {
        return Optional.ofNullable(Jwts.parserBuilder().setSigningKey(key()).build()
                        .parseClaimsJws(token).getBody().getId())
                .map(UUID::fromString)
                .orElse(null);
    }

    /**
     * Метод для отримання імені користувача з JWT токена.
     *
     * @param token String - JWT токен
     * @return String - ім'я користувача
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Метод для отримання ролей користувача з JWT токена.
     *
     * @param token String - JWT токен
     * @return Claims - об'єкт з ролями користувача
     */
    public Claims getUserRolesFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody();
    }

    /**
     * Метод для валідації JWT токена.
     *
     * @param authToken String - JWT токен
     * @return boolean - true, якщо токен валідний, інакше false
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
