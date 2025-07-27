package ru.transport.rent.exceptions;

import java.io.Serial;

/**
 * Исключение, выбрасываемое когда пользователь пытается арендовать собственный транспорт.
 */
public class SelfRentalNotAllowedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public SelfRentalNotAllowedException(String message) {
        super(message);
    }
}
