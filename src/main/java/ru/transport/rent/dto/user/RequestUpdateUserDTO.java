package ru.transport.rent.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * ДТО для обновления информации об аккаунте.
 */
@Data
@Builder
public class RequestUpdateUserDTO {

    @JsonProperty("username")
    private String userName;
    private String password;

}