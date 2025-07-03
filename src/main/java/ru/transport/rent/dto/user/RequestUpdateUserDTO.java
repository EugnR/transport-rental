package ru.transport.rent.dto.user;

import lombok.Builder;
import lombok.Data;

/**
 * ДТО для обновления информации об аккаунте.
 */
@Data
@Builder
public class RequestUpdateUserDTO {

    private String userName;
    private String password;

}