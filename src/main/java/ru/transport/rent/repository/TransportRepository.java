package ru.transport.rent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.transport.rent.entity.Transport;


/**
 * Интерфейс для репозитория Transport(транспорт) с его особыми для него запросами.
 */
@Repository
public interface TransportRepository extends JpaRepository<Transport, Long> {

//    /**
//     * Получение списка транспорта по id пользователя.
//     * @param userId айдишник пользователя, по которому ведётся поиск
//     * @return список транспортов связанных с пользователем
//     */
//    List<Transport> findByUserId(Long userId);
}
