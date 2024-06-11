package com.example.URL_Shortener;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.URL_Shortener.security.data.dto.UserDataDto;
import com.example.URL_Shortener.security.data.entity.User;
import com.example.URL_Shortener.security.repository.UserRepository;
import com.example.URL_Shortener.security.repository.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testAddUser_success() {
        UserDataDto userDataDto = UserDataDto.builder()
                .login("testUser")
                .password("password")
                .build();

        when(userRepository.save(any(User.class))).thenReturn(new User());

        boolean result = userService.addUser(userDataDto);
        assertTrue(result);
    }

    @Test
    public void testAddUser_failure() {
        UserDataDto userDataDto = UserDataDto.builder()
                .login("testUser")
                .password("password")
                .build();

        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException());

        boolean result = userService.addUser(userDataDto);
        assertFalse(result);
    }

    @Test
    public void testFindUserByLogin_success() {
        String login = "testUser";
        User user = User.builder()
                .login(login)
                .password("password")
                .build();
        UserDataDto userDataDto = UserDataDto.fromUser(user);

        when(userRepository.findUserByLogin(login)).thenReturn(Optional.of(user));

        UserDataDto result = userService.findUserByLogin(login);
        assertEquals(userDataDto, result);
    }

    @Test
    public void testFindUserByLogin_failure() {
        String login = "testUser";

        when(userRepository.findUserByLogin(login)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.findUserByLogin(login));
    }
}