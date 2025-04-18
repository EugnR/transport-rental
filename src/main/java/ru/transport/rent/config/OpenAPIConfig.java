package ru.transport.rent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;


@Configuration
public class OpenAPIConfig {

    protected OpenAPIConfig() {

    }


    /**
     * Добавление поля под JWT в auto-generated swagger.
     *
     * @return конфигурация OpenAPI.
     */
    @Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "Bearer JWT Authorization";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

}
