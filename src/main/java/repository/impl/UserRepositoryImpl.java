package repository.impl;

import entity.User;
import repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для хранения пользователей/аккаунтов.
 */
public class UserRepositoryImpl extends CrudRepositoryImpl<User> implements UserRepository {

    private static UserRepositoryImpl instance;

    private final RentRepositoryImpl rentRepository = RentRepositoryImpl.getInstance();
    private final TransportRepositoryImpl transportRepository = TransportRepositoryImpl.getInstance();

    /**
     * Получение экземпляра репозитория.
     */
    public static UserRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }


    /**
     * Удаление пользователя по id.
     * @param id айдишник удаляемого пользователя
     */
    @Override
    public void deleteById(final Long id) {
        final boolean hasRents = !rentRepository.findByUserId(id).isEmpty();
        final boolean hasTransports = !transportRepository.findByUserId(id).isEmpty();

        if (hasRents) {
            throw new IllegalStateException("Невозможно удалить пользователя: существуют аренды с этим пользователем.");
        }
        if (hasTransports) {
            throw new IllegalStateException("Невозможно удалить пользователя: есть зарегистрированный на него транспорт.");
        }

        final List<User> listOfUsers = super.getAll();
        final Optional<User> first = Optional.ofNullable(listOfUsers.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с id " + id + " не найден")));
        first.ifPresent(listOfUsers::remove);
    }
}
