package com.example.URL_Shortener.config.jwt;

import com.example.URL_Shortener.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * Клас, що реалізує інтерфейс UserDetails для роботи з даними користувача в контексті Spring Security
 */
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String login;

    @JsonIgnore
    private String password;

    /**
     * Конструктор для створення UserDetailsImpl без пароля
     *
     * @param id UUID - ідентифікатор користувача
     * @param login String - логін користувача
     */
    public UserDetailsImpl(UUID id, String login) {
        this.id = id;
        this.login = login;
    }

    /**
     * Конструктор для створення UserDetailsImpl з паролем.
     *
     * @param id UUID - ідентифікатор користувача
     * @param login String - логін користувача
     * @param password String - пароль користувача
     */
    public UserDetailsImpl(UUID id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    /**
     * Метод для створення UserDetailsImpl на основі об'єкта User.
     *
     * @param user User - об'єкт користувача
     * @return UserDetailsImpl - об'єкт UserDetailsImpl
     */
    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(user.getId(),
                user.getLogin(),
                user.getPassword());
    }

    /**
     * Метод для отримання ідентифікатора користувача.
     *
     * @return UUID - ідентифікатор користувача
     */
    public UUID getId() {
        return id;
    }

    /**
     * Метод для отримання ролей користувача.
     *
     * @return Collection<? extends GrantedAuthority> - колекція ролей користувача
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * Метод для отримання пароля користувача.
     *
     * @return String - пароль користувача
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Метод для отримання логіна користувача.
     *
     * @return String - логін користувача
     */
    @Override
    public String getUsername() {
        return login;
    }

    /**
     * Метод для перевірки, чи не закінчився термін дії акаунта.
     *
     * @return boolean - true, якщо термін дії акаунта не закінчився
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Метод для перевірки, чи не заблокований акаунт.
     *
     * @return boolean - true, якщо акаунт не заблокований
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Метод для перевірки, чи не закінчився термін дії облікових даних.
     *
     * @return boolean - true, якщо термін дії облікових даних не закінчився
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Метод для перевірки, чи активований акаунт.
     *
     * @return boolean - true, якщо акаунт активований
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Метод для перевірки рівності об'єктів UserDetailsImpl.
     *
     * @param o Object - об'єкт для порівняння
     * @return boolean - true, якщо об'єкти рівні
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
