package ru.transport.rent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.transport.rent.entity.Rent;
import ru.transport.rent.entity.Transport;


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


    /**
     * Метод для поиска любого транспорта в радиусе от заданной точки.
     * Проверка на вхождение в радиус поиска ведётся по формуле хаверсина.
     * @param latitude широта центра круга поиска.
     * @param longitude долгота центра круга поиска.
     * @param radius радиус круга поиска в километрах.
     */
    @Query(value = """
    SELECT * FROM transport t
    WHERE (6371 * acos(cos(radians(:latitude)) * cos(radians(t.latitude)) *
           cos(radians(t.longitude) - radians(:longitude)) +
           sin(radians(:latitude)) * sin(radians(t.latitude)))) <= :radius
    """, nativeQuery = true)
    List<Transport> findAllAvailableTransportInRadius(@Param("latitude") double latitude,
                                           @Param("longitude") double longitude,
                                           @Param("radius") double radius);

    /**
     * Метод для поиска транспорта определённого типа в радиусе от заданной точки.
     * Проверка на вхождение в радиус поиска ведётся по формуле хаверсина.
     * @param latitude широта центра круга поиска.
     * @param longitude долгота центра круга поиска.
     * @param radius радиус круга поиска в километрах.
     * @param type тип искомого транспорта.
     */
    @Query(value = """
    SELECT * FROM transport t
    WHERE (6371 * acos(cos(radians(:latitude)) * cos(radians(t.latitude)) *
           cos(radians(t.longitude) - radians(:longitude)) +
           sin(radians(:latitude)) * sin(radians(t.latitude)))) <= :radius
    AND t.transport_type = :transport_type
    """, nativeQuery = true)
    List<Transport> findSpecificTransportsInRadius(@Param("latitude") double latitude,
                                           @Param("longitude") double longitude,
                                           @Param("radius") double radius,
                                           @Param("transport_type") String type);
}
