package repository;

import entity.Transport;
/**
 * Интерфейс для репозитория Transport(транспорт) с его особыми для него запросами.
 */
public interface TransportRepository extends CrudRepository<Transport> {
    //особых запросов пока нет
}
