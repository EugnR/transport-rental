package repository.impl;

import entity.Transport;
import repository.TransportRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Интерфейс для хранения транспортных средств.
 */
public class TransportRepositoryImpl extends CrudRepositoryImpl<Transport> implements TransportRepository {
    private static TransportRepositoryImpl instance;

    private final RentRepositoryImpl rentRepository = RentRepositoryImpl.getInstance();


    /**
     * Получение экземпляра репозитория.
     */
    public static TransportRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new TransportRepositoryImpl();
        }
        return instance;
    }

    /**
     * Удаление транспорта по id.
     * Пока есть аренды с транспортом, его удалить нельзя.
     * @param id айдишник удаляемого транспорта.
     */
    @Override
    public void deleteById(final Long id) {
        final boolean hasRents = !rentRepository.findByTransportId(id).isEmpty();

        if (hasRents) {
            throw new IllegalStateException("Невозможно удалить транспорт: существуют аренды с этим транспортом.");
        }

        final List<Transport> listOfTransports = super.getAll();
        final Optional<Transport> first = Optional.ofNullable(listOfTransports.stream()
                .filter(transport -> transport.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Транспорт с id " + id + " не найден")));
        first.ifPresent(listOfTransports::remove);
    }


    /**
     * Получение списка транспорта по id пользователя.
     * @param userId айдишник пользователя, по которому ведётся поиск
     * @return список транспортов связанных с пользователем
     */
    @Override
    public List<Transport> findByUserId(final Long userId) {
        return getAll().stream()
                .filter(transport -> transport.getOwnerId().equals(userId))
                .collect(Collectors.toList());
    }
}
