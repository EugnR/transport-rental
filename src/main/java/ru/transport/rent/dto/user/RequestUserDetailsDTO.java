package ru.transport.rent.dto.user;

import lombok.Builder;
import lombok.Data;

/**
 * ДТО для получения данных об аккаунте.
 */
@Data
@Builder
public class RequestUserDetailsDTO {

    private String userName;
    private Double balance;
    private String roleName;
}
