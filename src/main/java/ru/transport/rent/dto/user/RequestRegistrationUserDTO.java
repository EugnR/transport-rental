package ru.transport.rent.dto.user;

import lombok.Builder;
import lombok.Data;


/**
 * ДТО для регистрации.
 */
@Data
@Builder
public class RequestRegistrationUserDTO {

    private String userName;
    private String password;

}
