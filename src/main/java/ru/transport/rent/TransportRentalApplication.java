package ru.transport.rent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@SpringBootApplication(scanBasePackages = {
        "ru.transport.rent"
})
@OpenAPIDefinition(info = @Info(title = "transport-rental", version = "v1"))
@EnableConfigurationProperties
@EntityScan("ru.transport.rent")
@EnableAsync
@SuppressWarnings("PMD.UseUtilityClass")
public class TransportRentalApplication {
    /**
     * Program entry point.
     * @param args - аргументы вызова приложения
     */
    public static void main(final String[] args) {
        SpringApplication.run(TransportRentalApplication.class, args);
    }

}
