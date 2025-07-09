package ru.transport.rent.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * ДТО для авторизации.
 */
@Data
@Builder
public class RequestSignInUserDTO {

    @JsonProperty("username")
    private String userName;
    private String password;

}
