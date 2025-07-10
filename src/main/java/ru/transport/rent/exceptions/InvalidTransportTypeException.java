package ru.transport.rent.exceptions;

public class InvalidTransportTypeException extends RuntimeException {
    public InvalidTransportTypeException(String message) {
        super(message);
    }
}
