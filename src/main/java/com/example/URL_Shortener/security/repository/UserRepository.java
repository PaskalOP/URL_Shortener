package com.example.URL_Shortener.security.repository;

import com.example.URL_Shortener.security.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByLogin(String login);

    Boolean existsByLogin(String username);
}