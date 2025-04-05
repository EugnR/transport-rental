package repository;

import entity.User;

/**
 * Интерфейс для репозитория User(аккаунт) с его особыми для него запросами.
 */
public interface UserRepository extends CrudRepository<User> {
    //особых запросов пока нет


}
