package ru.transport.rent.exceptions;

import java.io.Serial;

/**
 * Исключение, выбрасываемое при обнаружении невалидного типа транспорта.
 */
public class InvalidTransportTypeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidTransportTypeException(final String message) {
        super(message);
    }
}
