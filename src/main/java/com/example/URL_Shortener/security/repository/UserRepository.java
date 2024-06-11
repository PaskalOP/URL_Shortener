package com.example.URL_Shortener.security.repository;

import com.example.URL_Shortener.security.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByLogin(String login);

    Boolean existsByLogin(String username);
}