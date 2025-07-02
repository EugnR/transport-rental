package ru.transport.rent.entity;

import java.util.Objects;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


/**
 * Родитель для сущностей.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass())  {
            return false;
        }
        final AbstractBaseEntity that = (AbstractBaseEntity) o;

        if (id != null && that.id != null) {
            return id.equals(that.id);
        }
        return false;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
