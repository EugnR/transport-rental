package ru.transport.rent.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.transport.rent.entity.User;
import ru.transport.rent.repository.UserRepository;

import lombok.RequiredArgsConstructor;


/**
 * Создание кастомного сервиса UserDetails.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    /**
     * Метод для нахождения пользователя по username.
     */
    @Override
    public UserDetails loadUserByUsername(final String username) {

        final Optional<User> userAlready = userRepository.findAll()
                .stream()
                .filter(account -> account.getUserName()
                        .equals(username))
                .findFirst();

        if (userAlready.isEmpty()) {
            throw new UsernameNotFoundException("Didn't find such  account");
        } else {
            return new UserDetailsImpl(userAlready.get());
        }
    }

}
