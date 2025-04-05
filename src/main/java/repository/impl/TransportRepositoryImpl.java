package repository.impl;

import entity.Transport;
import repository.TransportRepository;

/**
 * Интерфейс для хранения транспортных средств.
 */
public class TransportRepositoryImpl extends CrudRepositoryImpl<Transport> implements TransportRepository {
    private static TransportRepositoryImpl instance;

    /**
     * Получение экземпляра репозитория.
     */
    public static TransportRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new TransportRepositoryImpl();
        }
        return instance;
    }



}
