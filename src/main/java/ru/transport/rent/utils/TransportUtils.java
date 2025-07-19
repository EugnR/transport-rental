package ru.transport.rent.utils;

/**
 * Утилитарный класс для всяких методов используемых в логике работы с транспортом.
 */
public final class TransportUtils {

    private TransportUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Метод переводит первую букву строки в верхний регистр, а последующие - в нижний.
     */
    public static String normalizeTransportType(String type) {
        if (type == null || type.isBlank()) {
            return null;
        }
        String lowered = type.trim().toLowerCase();
        return lowered.substring(0, 1).toUpperCase() + lowered.substring(1);
    }

}
