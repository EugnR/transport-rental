package ru.transport.rent.config;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Конфигурационный класс, который считывает валидные типы транспорта из application.yml.
 */
@Component
@ConfigurationProperties(prefix = "transport")
public class TransportTypesConfig {

    private Set<String> validTypes;

    /**
     * Метод для получения валидных типов транспорта.
     */
    public Set<String> getValidTypesAsSet() {
        return validTypes.stream()
                .map(String::trim)
                .collect(Collectors.toUnmodifiableSet());
    }

    public void setValidTypes(final Set<String> validTypes) {
        this.validTypes = validTypes;
    }
}
