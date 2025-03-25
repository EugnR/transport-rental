package repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import entity.User;


/**
 * Интерфейс для хранения объектов User.
 */
@Repository
public interface UserRepository {

    /**
     * Сохранение пользователя.
     */
    void save(User user);

    /**
     * Удаление пользователя.
     */
    void delete(Long id);

    /**
     * Получение пользователя по id.
     */
    Optional<User> getById(Long id);

}
