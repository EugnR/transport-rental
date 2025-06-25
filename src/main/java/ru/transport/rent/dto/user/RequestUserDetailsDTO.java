package ru.transport.rent.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestUserDetailsDTO
{
    private String userName;
    private Double balance;
    private String roleName;
}
