package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Класс для пользователя.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class User extends BaseEntity {

    //private long userId; можно убрать тк в BaseEntity есть поле entityID?
    private String userName;
    private String password;
    private boolean isAdmin;
    private double balance;


}
