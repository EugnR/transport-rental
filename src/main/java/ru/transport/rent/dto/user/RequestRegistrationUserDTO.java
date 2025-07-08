package ru.transport.rent.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;


/**
 * ДТО для регистрации.
 */
@Data
@Builder
public class RequestRegistrationUserDTO {

    @JsonProperty("username")
    private String userName;
    private String password;

}
