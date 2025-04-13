package repository.impl;

import entity.Rent;
import repository.RentRepository;

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
    public void deleteById(Long id) {
        List<Rent> listOfRents = super.getAll();
        Optional<Rent> first = Optional.ofNullable(listOfRents.stream()
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
    public List<Rent> findByTransportId(Long transportId) {
        return getAll().stream()
                .filter(rent -> rent.getTransportId().equals(transportId))
                .collect(Collectors.toList());
    }
}
