package ru.transport.rent.repository;

import ru.transport.rent.entity.Rent;

import java.util.List;

/**
 * Интерфейс для репозитория Rent(аренда) с его особыми для него запросами.
 */
public interface RentRepository extends CrudRepository<Rent> {

    /**
     * Получение списка аренд по id транспорта.
     */
    List<Rent> findByTransportId(Long transportId);

    /**
     * Получение списка аренд по id пользователя.
     * @param userId айдишник пользователя, по которому проводится поиск
     * @return список аренд связанных с пользователем
     */
    List<Rent> findByUserId(Long userId);
}
