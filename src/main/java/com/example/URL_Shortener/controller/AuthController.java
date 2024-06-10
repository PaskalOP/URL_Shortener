package com.example.URL_Shortener.controller;

import com.example.URL_Shortener.config.jwt.TokenUtils;
import com.example.URL_Shortener.config.jwt.UserDetailsImpl;
import com.example.URL_Shortener.request.LoginRequest;
import com.example.URL_Shortener.request.SignupRequest;
import com.example.URL_Shortener.responseDTO.JwtResponce;
import com.example.URL_Shortener.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Контролер для автентифікації та реєстрації користувачів.
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/V1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenUtils jwtUtils;

    /**
     * Метод для аутентифікації користувача
     *
     * @param loginRequest LoginRequest - запит на вхід
     * @return ResponseEntity<JwtResponce> - відповідь з JWT токеном
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponce> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity
                .ok(new JwtResponce(jwt, userDetails.getId(), userDetails.getUsername()));
    }

    /**
     * Метод для реєстрації нового користувача.
     *
     * @param signUpRequest SignupRequest - запит на реєстрацію
     * @return ResponseEntity<?> - відповідь про успішну реєстрацію
     * @throws Exception - можливі винятки при реєстрації
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) throws Exception {
        userService.registerUser(signUpRequest.getLogin(),
                signUpRequest.getPassword());
        return ResponseEntity.accepted().build();
    }
}