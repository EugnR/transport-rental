package ru.transport.rent.config;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Конфигурационный класс, который считывает валидные типы транспорта из application.yml.
 */
@Component
@ConfigurationProperties(prefix = "transport")
@SuppressWarnings("PMD.AtLeastOneConstructor")
@Setter
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
}
