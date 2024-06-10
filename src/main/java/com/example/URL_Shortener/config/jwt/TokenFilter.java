package com.example.URL_Shortener.config.jwt;

import com.example.URL_Shortener.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * Фільтр для обробки та перевірки JWT токенів
 * Виконується один раз за запит.
 */
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenUtils jwtUtils;

    @Autowired
    private UserService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    /**
     * Метод для фільтрації запитів та перевірки JWT токенів
     *
     * @param request HttpServletRequest - поточний HTTP запит
     * @param response HttpServletResponse - поточна HTTP відповідь
     * @param filterChain FilterChain - ланцюжок фільтрів
     * @throws ServletException - можливе виключення сервлета
     * @throws IOException - можливе виключення вводу/виводу
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (Objects.nonNull(jwt) && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                UUID userId = jwtUtils.getUserIdFromJwtToken(jwt);

                UserDetails userDetails = new UserDetailsImpl(userId, username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, userDetails.getAuthorities(), userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Метод для парсингу JWT токена з заголовка запиту.
     *
     * @param request HttpServletRequest - поточний HTTP запит
     * @return String - JWT токен або null, якщо заголовок не містить токен
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}
