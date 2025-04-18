package ru.transport.rent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.transport.rent.entity.User;

/**
 * Интерфейс для репозитория User(аккаунт) с его особыми для него запросами.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {



}
