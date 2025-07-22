package ru.transport.rent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.transport.rent.entity.Rent;


/**
 * Интерфейс для репозитория Rent(аренда) с его особыми для него запросами.
 */
@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {

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
