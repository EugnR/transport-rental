package ru.transport.rent.dto.user;

import lombok.Builder;
import lombok.Data;

/**
 * ДТО возвращаемый на запрос о получении данных об аккаунте.
 */
@Data
@Builder
public class RequestUserDetailsDTO {

    private String userName;
    private Double balance;
    private String roleName;
}
