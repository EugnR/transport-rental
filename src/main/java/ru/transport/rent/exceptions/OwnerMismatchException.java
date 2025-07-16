package ru.transport.rent.exceptions;

import java.io.Serial;

/**
 * Исключение, выбрасываемое, когда пользователь пытается удалить транспорт, хозяином которого не является.
 */
public class OwnerMismatchException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public OwnerMismatchException(String message) {
        super(message);
    }
}
