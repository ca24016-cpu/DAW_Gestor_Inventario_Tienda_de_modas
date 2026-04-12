package com.inventario.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI fashionTrackAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestor de Inventario Tienda de Modas")
                        .version("1.0.0")
                        .description("API para gestión de inventario de tienda de moda - Laboratorio 2 DAW")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo - Laboratorio 2 DAW")
                                .email("andrea.chavez@ues.edu.sv")));
    }
}