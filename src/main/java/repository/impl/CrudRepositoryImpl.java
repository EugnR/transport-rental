package repository.impl;

import entity.AbstractBaseEntity;
import org.springframework.stereotype.Repository;
import repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Интерфейс для хранения сущностей.
 */
@Repository
public abstract class CrudRepositoryImpl<T extends AbstractBaseEntity> implements CrudRepository<T> {

    private final List<T> baseEntities = new ArrayList<>();

    protected CrudRepositoryImpl() {

    }



    /**
     * Сохранение сущности.
     */
    @Override
    public void save(final T baseEntity) {
        baseEntity.setId((long) baseEntities.size() + 1);
        baseEntities.add(baseEntity);
    }


    /**
     * Удаление сущности.
     */
    @Override
    public void deleteById(final Long id) {
        final Optional<AbstractBaseEntity> first = Optional.ofNullable(baseEntities.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new));

        first.ifPresent(baseEntities::remove);
    }


    /**
     * Получение сущности по id.
     */
    @Override
    public Optional<T> getById(final Long id) {
        return baseEntities.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    /**
     * Получение списка всех сущностей.
     */
    @Override
    public List<T> getAll() {
        return baseEntities;
    }

}
