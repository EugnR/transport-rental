package ru.transport.rent.repository.impl;

import ru.transport.rent.entity.Rent;
import ru.transport.rent.repository.RentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    /**
     * Удаление аренды по id.
     * @param id айдишник удаляемой аренды.
     */
    @Override
    public void deleteById(final Long id) {
        final List<Rent> listOfRents = super.getAll();
        final Optional<Rent> first = Optional.ofNullable(listOfRents.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new));
        first.ifPresent(listOfRents::remove);
    }

    /**
     * Поиск аренд по id транспорта.
     * @param transportId айдишник транспорта, по которому ведётся поиск аренд.
     * @return список найденных аренд.
     */
    @Override
    public List<Rent> findByTransportId(final Long transportId) {
        return getAll().stream()
                .filter(rent -> rent.getTransportId().equals(transportId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Rent> findByUserId(final Long userId) {
        return getAll().stream()
                .filter(rent -> rent.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
