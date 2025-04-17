package ru.transport.rent.repository;

import ru.transport.rent.entity.Transport;

import java.util.List;

/**
 * Интерфейс для репозитория Transport(транспорт) с его особыми для него запросами.
 */
public interface TransportRepository extends CrudRepository<Transport> {

    /**
     * Получение списка транспорта по id пользователя.
     * @param userId айдишник пользователя, по которому ведётся поиск
     * @return список транспортов связанных с пользователем
     */
    List<Transport> findByUserId(Long userId);
}
