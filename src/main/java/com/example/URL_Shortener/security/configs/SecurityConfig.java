package com.example.URL_Shortener.security.configs;

import com.example.URL_Shortener.security.exception.AuthHandler;
import com.example.URL_Shortener.security.utils.TokenFilter;
import com.example.URL_Shortener.security.repository.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Клас для конфігурації безпеки додатка.
 * Налаштовує аутентифікацію, управління сесіями та фільтри безпеки.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private AuthHandler unauthorizedHandler;

    @Autowired
    private PasswordEncoder encoder;

    /**
     * Метод для створення біну TokenFilter, який обробляє JWT токени.
     *
     * @return TokenFilter - фільтр для обробки JWT токенів
     */
    @Bean
    public TokenFilter authenticationJwtTokenFilter() {
        return new TokenFilter();
    }

    /**
     * Метод для створення біну DaoAuthenticationProvider, який використовує UserService та PasswordEncoder.
     *
     * @return DaoAuthenticationProvider - провайдер аутентифікації
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder);

        return authProvider;
    }

    /**
     * Метод для створення біну AuthenticationManager
     *
     * @param authConfig AuthenticationConfiguration - конфігурація аутентифікації
     * @return AuthenticationManager - менеджер аутентифікації
     * @throws Exception - можливе виключення
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Метод для налаштування ланцюжка фільтрів безпеки
     *
     * @param http HttpSecurity - об'єкт для налаштування безпеки HTTP
     * @return SecurityFilterChain - ланцюжок фільтрів безпеки
     * @throws Exception - можливе виключення
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/api/V1/shorter/**").authenticated()
                                .anyRequest().permitAll()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
