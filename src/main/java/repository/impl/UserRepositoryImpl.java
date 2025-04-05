package repository.impl;

import entity.User;
import repository.UserRepository;

/**
 * Интерфейс для хранения пользователей/аккаунтов.
 */
public class UserRepositoryImpl extends CrudRepositoryImpl<User> implements UserRepository {

    private static UserRepositoryImpl instance;

    /**
     * Получение экземпляра репозитория.
     */
    public static UserRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }
}
