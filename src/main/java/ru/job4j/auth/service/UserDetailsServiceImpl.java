package ru.job4j.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.Collections;

/**
 * Этот сервис будет загружать в SecurityHolder детали авторизованного пользователя.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PersonRepository personRepository;

    /**
     * UserDetails предоставляет необходимую информацию для построения объекта Authentication
     * из DAO объектов приложения или других источников данных системы безопасности.
     * Объект UserDetails содержит имя пользователя, пароль,
     * флаги: isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled
     * и Collection — прав (ролей) пользователя.
     * Позволяет получить из источника данных объект пользователя и сформировать из него
     * объект UserDetails, который будет использоваться контекстом Spring Security.
     *
     * @param username логин
     * @return UserDetails
     * @throws UsernameNotFoundException исключение
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByLogin(username).orElseThrow(
                () -> new UsernameNotFoundException(
                        "Пользователь с логином " + username + " не зарегистрирован!"));
        return new User(person.getLogin(), person.getPassword(), Collections.emptyList());
    }
}
