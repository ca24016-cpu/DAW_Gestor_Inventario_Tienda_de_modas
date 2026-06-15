package com.inventario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI fashionTrackAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestor de Inventario Tienda de Modas")
                        .version("1.0.0")
                        .description("API para gestión de inventario de tienda de moda - Proyecto Final DAW 2026")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo - Proyecto Final DAW 2026")
                                .email("andrea.chavez@ues.edu.sv")));
    }
}