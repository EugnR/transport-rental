package ru.transport.rent.exceptions;

import java.io.Serial;

/**
 * Исключение, выбрасываемое, при попытке модифицировать или удалить ресурс, владельцем которого является другой пользователь.
 */
public class OwnerMismatchException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public OwnerMismatchException(final String message) {
        super(message);
    }
}
