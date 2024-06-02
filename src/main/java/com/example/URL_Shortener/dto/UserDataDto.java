package com.example.URL_Shortener.dto;

import com.example.URL_Shortener.entity.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDataDto {
    private String login;
    private String password;

    public User toUser() {
        return User.builder()
                .login(login)
                .password(password)
                .build();
    }

    public static UserDataDto fromUser(User user) {
        return UserDataDto.builder()
                .login(user.getLogin())
                .password(user.getPassword())
                .build();
    }
}
