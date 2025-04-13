package repository;

import entity.Rent;

import java.util.List;

/**
 * Интерфейс для репозитория Rent(аренда) с его особыми для него запросами.
 */
public interface RentRepository extends CrudRepository<Rent> {

    /**
     * Получение списка аренд по id транспорта
     */
    List<Rent> findByTransportId(Long transportId);
}
