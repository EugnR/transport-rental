package repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Интерфейс с общими для всех сущностей операциями.
 */
@Repository
public interface CrudRepository<T> {

    /**
     * Сохранение сущности.
     */
    void save(T entity);

    /**
     * Удаление сущности.
     */
    void deleteById(Long id);

    /**
     * Получение сущности по id.
     */
    Optional<T> getById(Long id);

    /**
     * Получение списка всех сущностей.
     */
    List<T> getAll();



}
