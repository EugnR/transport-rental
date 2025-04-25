package ru.transport.rent.dto.user;

import lombok.Builder;
import lombok.Data;

/**
 * ДТО для авторизации.
 */
@Data
@Builder
public class RequestSingInUserDTO {

    private String userName;
    private String password;

}
