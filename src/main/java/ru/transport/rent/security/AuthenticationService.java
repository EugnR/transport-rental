package ru.transport.rent.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.transport.rent.entity.User;

/**
 * Утилитарный класс для работы с методами аутентификации.
 */
public final class AuthenticationService {

    private AuthenticationService() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Метод для получения пользователя из Spring Security Context.
     */
    public static User getUserFromSecurityContext() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
        || (authentication.getPrincipal() instanceof String)) {
            throw new AccessDeniedException("Need to be authenticated to use this endpoint");
        }
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getUser();
    }
}
