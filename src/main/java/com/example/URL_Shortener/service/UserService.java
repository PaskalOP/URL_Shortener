package com.example.URL_Shortener.service;

import com.example.URL_Shortener.dto.UserDataDto;
import com.example.URL_Shortener.entity.User;
import com.example.URL_Shortener.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean addUser(UserDataDto userDataDto) {
        try {
            userRepository.save(userDataDto.toUser());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UserDataDto findUserByLogin(String login) {
        return userRepository
                .findUserByLogin(login)
                .map(UserDataDto::fromUser)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}