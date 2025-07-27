package ru.transport.rent.utils;

import java.util.Locale;

/**
 * Утилитарный класс для всяких методов используемых в логике.
 */
public final class CustomUtils {

    private CustomUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Метод переводит первую букву строки в верхний регистр, а последующие - в нижний.
     */
    public static String CapitalizeFirst(final String type) {
        if (type == null || type.isBlank()) {
            return null;
        }
        final String lowered = type.trim().toLowerCase(Locale.ROOT);
        return lowered.substring(0, 1).toUpperCase(Locale.ROOT) + lowered.substring(1);
    }

}
