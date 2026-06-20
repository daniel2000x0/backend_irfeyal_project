package com.irfeyal.asistencia.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "Bearer JWT";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("IRFEYAL - Módulo de Asistencia API")
                .version("1.0.0")
                .description("API REST para la gestión de asistencias del sistema educativo IRFEYAL. "
                    + "Provee CRUD de asistencias y clases, filtros dinámicos, y reportes PDF.")
                .contact(new Contact()
                    .name("IRFEYAL")
                    .email("contacto@irfeyal.edu.ec")
                    .url("https://irfeyal.edu.ec"))
                .license(new License()
                    .name("MIT")
                    .url("https://opensource.org/licenses/MIT")))
            .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
            .components(new Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME,
                    new SecurityScheme()
                        .name(SECURITY_SCHEME_NAME)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
    }

}
