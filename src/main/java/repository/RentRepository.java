package repository;

import entity.Rent;
/**
 * Интерфейс для репозитория Rent(аренда) с его особыми для него запросами.
 */
public interface RentRepository extends CrudRepository<Rent> {
    //особых запросов пока нет
}
