package repository.impl;

import entity.Rent;
import repository.RentRepository;
/**
 * Интерфейс для хранения записей об арендах.
 */
public class RentRepositoryImpl extends CrudRepositoryImpl<Rent> implements RentRepository {
    private static RentRepositoryImpl instance;

    /**
     * Получение экземпляра репозитория.
     */
    public static RentRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new RentRepositoryImpl();
        }
        return instance;
    }
}
