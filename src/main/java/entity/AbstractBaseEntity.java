package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Родитель для сущностей.
 */
//TODO добавить jpa-аннотацию @Version при подключении к проекту настоящей БД
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractBaseEntity {
    private Long id;
}
