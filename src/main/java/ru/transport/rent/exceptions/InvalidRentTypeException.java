package ru.transport.rent.exceptions;

import java.io.Serial;

/**
 * Исключение, выбрасываемое при обнаружении невалидного типа аренды.
 */
public class InvalidRentTypeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidRentTypeException(final String message) {
        super(message);
    }
}
