package com.example.URL_Shortener.security.repository;

import com.example.URL_Shortener.security.data.UserDetailsImpl;
import com.example.URL_Shortener.security.data.dto.UserDataDto;
import com.example.URL_Shortener.security.data.entity.User;
import com.example.URL_Shortener.security.exception.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
   @Autowired private PasswordEncoder encoder;

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
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with login: " + login));
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + login));

        return UserDetailsImpl.build(user);
    }

    @Transactional
    public void registerUser(String login, String password) throws UserAlreadyExistException {
        if (userRepository.existsByLogin(login)) {
            throw new UserAlreadyExistException(login);
        }
        User user = new User();
        user.setLogin(login);
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
    }
}