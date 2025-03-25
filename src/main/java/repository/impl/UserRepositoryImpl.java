package repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import entity.User;
import repository.UserRepository;


/**
 * Интерфейс для хранения объектов User.
 */
@Repository
public final class UserRepositoryImpl implements UserRepository {

    private static UserRepositoryImpl instanse;

    private final List<User> users = new ArrayList<User>();


    private UserRepositoryImpl() {

    }


    /**
     * Получение экземпляра репозитория.
     */
    public static UserRepositoryImpl getInstance() {
        if (instanse == null) {
            instanse = new UserRepositoryImpl();
        }
        return instanse;
    }


    /**
     * Сохранение пользователя.
     */
    @Override
    public void save(User user) {
        users.add(user);
    }


    /**
     * Удаление пользователя.
     */
    @Override
    public void delete(Long id) {
        Optional<User> first = Optional.ofNullable(users.stream()
                .filter(item -> item.getUserId() == id)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new));

        first.ifPresent(users::remove);
    }


    /**
     * Получение пользователя по id.
     */
    @Override
    public Optional<User> getById(Long id) {
        return users.stream()
                .filter(item -> item.getUserId() == id)
                .findFirst();
    }

}
